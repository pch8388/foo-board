package me.study.foostudy.board.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Getter;
import me.study.foostudy.common.domain.BaseEntity;

@Getter
@Document("post")
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
