package me.study.foostudy.board.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PostTest {

	@Test
	@DisplayName("글 등록")
	void createPost() {
		Post newPost = Post.builder()
			.title("New Title")
			.content("New Content")
			.build();

		assertThat(newPost.getTitle()).isEqualTo("New Title");
		assertThat(newPost.getContent()).isEqualTo("New Content");
	}
}