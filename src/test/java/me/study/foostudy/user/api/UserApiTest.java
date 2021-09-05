package me.study.foostudy.user.api;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import me.study.foostudy.user.application.UserService;
import me.study.foostudy.user.domain.User;
import me.study.foostudy.user.dto.RequestUserDto;
import me.study.foostudy.user.dto.ResponseUserDto;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@AutoConfigureWebTestClient
class UserApiTest {

	private UserService userService;
	private WebTestClient client;

	@BeforeEach
	void setUp() {
		userService = mock(UserService.class);
		client = WebTestClient.bindToController(new UserApi(userService)).build();
	}

	@DisplayName("유저 생성 테스트")
	@Test
	void register() {
		final String username = "register-user";
		final String password = "register-pass";

		when(userService.register(any()))
			.thenReturn(Mono.just(ResponseUserDto.convertFromEntity(User.builder()
																		.username(username)
																		.password(password)
																		.authorities(List.of(new SimpleGrantedAuthority("ROLE_USER")))
																		.build())));


		client.post().uri("/users")
			.body(BodyInserters.fromPublisher(Mono.just(new RequestUserDto(username, password)),
				RequestUserDto.class))
			.exchange()
			.expectHeader().contentType(MediaType.APPLICATION_JSON)
			.expectStatus().isCreated()
			.returnResult(ResponseUserDto.class)
			.getResponseBody()
			.as(StepVerifier::create)
			.expectNextMatches(responseUser -> {
				assertThat(responseUser).isNotNull();
				assertThat(responseUser.getUsername()).isEqualTo(username);
				return true;
			})
			.expectComplete()
			.verify();
	}
}