package me.study.foostudy.board.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.study.foostudy.board.domain.Post;
import me.study.foostudy.common.utils.RequestMapper;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RequestPostDto implements RequestMapper<Post> {
	@NotEmpty(message = "Title cannot be empty")
	private String title;

	@NotNull(message = "Content cannot be null")
	@Size(min = 1, max = 10_000, message = "Content must be between 1 and 10000 characters")
	private String content;

	@Override
	public Post toEntity() {
		return Post.builder()
			.title(title)
			.content(content)
			.build();
	}
}
