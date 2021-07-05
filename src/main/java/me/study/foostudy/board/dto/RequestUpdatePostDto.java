package me.study.foostudy.board.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequestUpdatePostDto {

	@NotNull(message = "Content cannot be null")
	@Size(min = 1, max = 10_000, message = "Content must be between 1 and 10000 characters")
	private String updateContent;
	public RequestUpdatePostDto(String updateContent) {
		this.updateContent = updateContent;
	}
}
