package me.study.foostudy.board.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.study.foostudy.board.domain.Post;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RequestPostDto {
	@NotBlank(message = "Title cannot be empty")
	private String title;

	@NotBlank(message = "Content cannot be empty")
	@Size(min = 1, max = 10_000, message = "Content must be between 1 and 10000 characters")
	private String content;

	public Post toEntityWithWriter(String userId) {
		return Post.builder()
			.title(title)
			.content(content)
			.userId(userId)
			.build();
	}
}
