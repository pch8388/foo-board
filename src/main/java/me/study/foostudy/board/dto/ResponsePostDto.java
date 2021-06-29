package me.study.foostudy.board.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import me.study.foostudy.board.domain.Post;

@Getter
@NoArgsConstructor
public class ResponsePostDto {

	private String id;
	private String title;
	private String content;
	private LocalDateTime createdDate;
	private LocalDateTime modifiedDate;

	private ResponsePostDto(Post post) {
		this.id = post.getId();
		this.title = post.getTitle();
		this.content = post.getContent();
		this.createdDate = post.getCreatedDate();
		this.modifiedDate = post.getModifiedDate();
	}
	public static ResponsePostDto convertFromEntity(Post post) {
		return new ResponsePostDto(post);
	}
}
