package me.study.foostudy.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import me.study.foostudy.user.domain.User;

@Getter
@NoArgsConstructor
public class ResponseUserDto {
	private String username;

	private ResponseUserDto(String username) {
		this.username = username;
	}

	public static ResponseUserDto convertFromEntity(User user) {
		return new ResponseUserDto(user.getUsername());
	}
}
