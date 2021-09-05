package me.study.foostudy.board.api;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.*;
import static org.springframework.web.reactive.function.client.ExchangeFilterFunctions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import me.study.foostudy.board.application.PostService;
import me.study.foostudy.board.domain.Post;
import me.study.foostudy.board.dto.RequestPostDto;
import me.study.foostudy.board.dto.ResponsePostDto;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@AutoConfigureWebTestClient
class PostApiTest {

	private PostService postService;
	private WebTestClient client;

	@BeforeEach
	void setUp() {
		postService = mock(PostService.class);
		client = WebTestClient
			.bindToController(new PostApi(postService))
			.apply(springSecurity())
			.configureClient()
			.filter(basicAuthentication("test", "test"))
			.build();
	}

	@DisplayName("게시글 저장")
	@Test
	void savePost() {
		// given
		final String title = "Test";
		final String content = "test111";
		final String userId = "test";

		// when
		when(postService.saveNewPost(any(), any()))
			.thenReturn(Mono.just(ResponsePostDto.convertFromEntity(Post.builder().title(title).content(content).userId(
				userId).build())));

		// then
		client
			.post().uri("/posts")
			.body(BodyInserters.fromPublisher(Mono.just(new RequestPostDto(title, content)),
				RequestPostDto.class))
			.exchange()
			.expectHeader().contentType(MediaType.APPLICATION_JSON)
			.expectStatus().isCreated()
			.returnResult(ResponsePostDto.class)
			.getResponseBody()
			.as(StepVerifier::create)
			.expectNextMatches(responseDto -> {
				assertThat(responseDto).isNotNull();
				assertThat(responseDto.getTitle()).isEqualTo(title);
				assertThat(responseDto.getContent()).isEqualTo(content);
				return true;
			})
			.expectComplete()
			.verify();
	}

	@DisplayName("게시글 저장 - validation error")
	@ParameterizedTest
	@CsvSource({"'', test111", "test, ''", "'', ''"})
	void savePost_valid_exception(String title, String content) {

		// when, then
		client
			.post().uri("/posts")
			.body(BodyInserters.fromPublisher(Mono.just(new RequestPostDto(title, content)),
				RequestPostDto.class))
			.exchange()
			.expectStatus().isBadRequest()
			.returnResult(ResponsePostDto.class)
			.getResponseBody()
			.as(StepVerifier::create)
			.expectComplete()
			.verify();
	}

	@DisplayName("게시글 상세 조회")
	@Test
	void searchPostsById() {
		// given
		final String title = "title";
		final String content = "content";
		final String userId = "test";
		final Post post = Post.builder()
			.title(title)
			.content(content)
			.userId(userId)
			.build();

		// when
		when(postService.findPostsById(any()))
			.thenReturn(Mono.just(ResponsePostDto.convertFromEntity(post)));

		// then
		client
			.get().uri("/posts/{postId}", "test-id")
			.exchange()
			.expectStatus().isOk()
			.returnResult(ResponsePostDto.class)
			.getResponseBody()
			.as(StepVerifier::create)
			.expectNextMatches(dto -> {
				assertThat(dto).isNotNull();
				assertThat(dto.getTitle()).isEqualTo(title);
				assertThat(dto.getContent()).isEqualTo(content);
				return true;
			})
			.expectComplete()
			.verify();
	}
}