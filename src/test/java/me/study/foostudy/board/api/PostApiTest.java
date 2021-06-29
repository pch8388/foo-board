package me.study.foostudy.board.api;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
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

@SpringBootTest
@AutoConfigureWebTestClient
class PostApiTest {

	@Autowired
	private WebTestClient client;
	private PostService postService;
	private PostApi postApi;

	@BeforeEach
	void setUp() {
		postService = Mockito.mock(PostService.class);
		postApi = new PostApi(postService);
	}

	@Test
	void test() {
		// given
		final String title = "Test";
		final String content = "test111";

		// when
		Mockito.when(postService.saveNewPost(any()))
			.thenReturn(Mono.just(Post.builder().title(title).content(content).build()));

		// then
		WebTestClient
			.bindToController(postApi)
			.build()
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

}