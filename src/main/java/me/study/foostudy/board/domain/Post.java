package me.study.foostudy.board.domain;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Post {

	@Id
	private String id;
	private String title;
	private String content;

	@CreatedDate
	private LocalDateTime createdDate;

	@LastModifiedDate
	private LocalDateTime modifiedDate;

	@Builder
	public Post(String title, String content) {
		this.title = title;
		this.content = content;
	}
}
