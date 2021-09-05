package me.study.foostudy.user.api;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import me.study.foostudy.user.application.UserService;
import me.study.foostudy.user.dto.RequestUserDto;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class UserApi {

	private final UserService userService;

	@PostMapping("/users")
	public Mono<ResponseEntity<?>> register(@RequestBody Mono<RequestUserDto> userDto) {
		return userDto.flatMap(userService::register)
			.map(savedItem ->
				ResponseEntity.created(URI.create("/users/" + savedItem.getUsername()))
					.body(savedItem));
	}
}
