package me.study.foostudy.board.domain;

import javax.validation.constraints.NotBlank;

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

	private String userId;

	@Builder
	public Post(String title, String content, String userId) {
		// TODO : 2021/08/29 argument validation 추가 필요   -ksc
		this.title = title;
		this.content = content;
		this.userId = userId;
	}

	public void updateContent(String content) {
		this.content = content;
	}
}
