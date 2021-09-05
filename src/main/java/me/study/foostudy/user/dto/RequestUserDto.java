package me.study.foostudy.user.dto;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.study.foostudy.user.domain.User;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RequestUserDto {

	private String username;
	private String password;

	public User createUser() {
		return User.builder()
			.username(username)
			.password(password)
			.authorities(List.of(new SimpleGrantedAuthority("ROLE_USER")))
			.build();
	}
}
