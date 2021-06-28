package me.study.foostudy.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.study.foostudy.board.domain.Post;
import me.study.foostudy.common.utils.RequestMapper;

@Getter
@AllArgsConstructor
public class RequestPostDto implements RequestMapper<Post> {
	private String title;
	private String content;

	@Override
	public Post toEntity() {
		return Post.builder()
			.title(title)
			.content(content)
			.build();
	}
}
