package me.study.foostudy.board.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PostTest {

	@DisplayName("글 등록")
	@Test
	void createPost() {
		Post newPost = Post.builder()
			.title("New Title")
			.content("New Content")
			.build();

		assertThat(newPost.getTitle()).isEqualTo("New Title");
		assertThat(newPost.getContent()).isEqualTo("New Content");
	}

	@DisplayName("글 수정")
	@Test
	void updatePost() {
		Post newPost = Post.builder()
			.title("New Title")
			.content("New Content")
			.build();

		newPost.updateContent("Update Content");

		assertThat(newPost.getTitle()).isEqualTo("New Title");
		assertThat(newPost.getContent()).isEqualTo("Update Content");
	}
}