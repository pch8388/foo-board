package me.study.foostudy.board.dto;

import lombok.Getter;
import me.study.foostudy.board.domain.Post;

@Getter
public class ResponsePostDto {

	private String id;
	private String title;
	private String content;

	private ResponsePostDto(Post post) {
		this.id = post.getId();
		this.title = post.getTitle();
		this.content = post.getContent();
	}
	public static ResponsePostDto convertFromEntity(Post post) {
		return new ResponsePostDto(post);
	}
}
