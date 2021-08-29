package me.study.foostudy.board.domain;

import static java.util.function.Predicate.*;

import java.util.stream.Stream;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.StringUtils;

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
		validateArguments(title, content, userId);
		this.title = title;
		this.content = content;
		this.userId = userId;
	}

	public void updateContent(String content) {
		this.content = content;
	}

	private void validateArguments(String title, String content, String userId) {
		if (isBlankExistsArgument(title, content, userId)) {
			throw new IllegalArgumentException("Post 필수값 누락");
		}
	}

	private boolean isBlankExistsArgument(String... args) {
		return Stream.of(args).anyMatch(not(StringUtils::hasText));
	}
}
