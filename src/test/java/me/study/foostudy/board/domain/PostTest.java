package me.study.foostudy.board.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import me.study.foostudy.board.exception.PostPermissionException;

class PostTest {

	@DisplayName("글 등록")
	@Test
	void createPost() {
		Post newPost = createPost("New Title", "New Content", "test-user");

		assertThat(newPost.getTitle()).isEqualTo("New Title");
		assertThat(newPost.getContent()).isEqualTo("New Content");
		assertThat(newPost.getUserId()).isEqualTo("test-user");
	}

	@DisplayName("글 수정")
	@Test
	void updatePost() {
		Post newPost = createPost("New Title", "New Content", "test-user");

		newPost.updateContent("test-user", "Update Content");

		assertThat(newPost.getTitle()).isEqualTo("New Title");
		assertThat(newPost.getContent()).isEqualTo("Update Content");
		assertThat(newPost.getUserId()).isEqualTo("test-user");
	}

	@DisplayName("글 수정 시 자신의 글이 아니면 예외 발생")
	@Test
	void updatePost_otherUser_exception() {
		Post newPost = createPost("New Title", "New Content", "test-user");

		assertThatThrownBy(
				() -> newPost.updateContent("other-user", "Update Content"))
			.isInstanceOf(PostPermissionException.class)
			.hasMessage("다른 유저의 게시글을 수정할 수 없습니다");

	}

	@DisplayName("필수 값이 없으면 예외 발생")
	@ParameterizedTest
	@CsvSource({"title, content, ", "title, , test-user", ", content, test-user"})
	void post_required_value_exception(final String title, final String content, final String userId) {
		assertThatThrownBy(() -> createPost(title, content, userId))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("Post 필수값 누락");
	}

	private Post createPost(final String title, final String content, final String userId) {
		return Post.builder()
			.title(title)
			.content(content)
			.userId(userId)
			.build();
	}
}