package me.study.foostudy.board.domain;

import org.springframework.data.annotation.Id;

import lombok.Builder;
import lombok.Getter;
import me.study.foostudy.common.domain.BaseEntity;

@Getter
public class Post extends BaseEntity {

	@Id
	private String id;
	private String title;
	private String content;

	@Builder
	public Post(String title, String content) {
		this.title = title;
		this.content = content;
	}

	public void updateContent(String content) {
		this.content = content;
	}
}
