package me.study.foostudy.user.dto;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

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

	public User createUser(PasswordEncoder passwordEncoder) {
		return User.builder()
			.username(username)
			.password(passwordEncoder.encode(password))
			.authorities(List.of(new SimpleGrantedAuthority("ROLE_USER")))
			.build();
	}
}
