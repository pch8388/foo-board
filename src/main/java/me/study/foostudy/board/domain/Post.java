package me.study.foostudy.board.domain;

import org.springframework.data.annotation.Id;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Post {

	@Id
	private String id;
	private String title;
	private String content;

	@Builder
	public Post(String title, String content) {
		this.title = title;
		this.content = content;
	}
}
