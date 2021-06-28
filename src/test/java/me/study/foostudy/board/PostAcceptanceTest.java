package me.study.foostudy.board;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.web.reactive.function.BodyInserters;

import me.study.foostudy.AcceptanceTest;
import me.study.foostudy.board.domain.Post;
import me.study.foostudy.board.dto.RequestPostDto;
import reactor.core.publisher.Mono;

@DisplayName("게시글")
public class PostAcceptanceTest extends AcceptanceTest {

	@DisplayName("게시글을 등록한다")
	@Test
	void createPost() {
		게시글_등록_되어있음("새로운 게시글 제목", "새로운 게시글 내용을 등록합니다.");
	}

	private void 게시글_등록_되어있음(String title, String content) {
		// given, when
		final Post responsePost = client.post().uri("/posts")
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON)
			.body(BodyInserters.fromPublisher(Mono.just(requestPost(title, content)), RequestPostDto.class))
			.exchange()
			.expectStatus().isCreated()
			.expectBody(Post.class)
			.consumeWith(document("post-new-item",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				requestFields(
					fieldWithPath("title").description("게시글 제목"),
					fieldWithPath("content").description("게시글 내용")
				),
				responseFields(
					fieldWithPath("id").type(JsonFieldType.STRING).description("게시글 id"),
					fieldWithPath("title").type(JsonFieldType.STRING).description("게시글 제목"),
					fieldWithPath("content").type(JsonFieldType.STRING).description("게시글 내용")
				)))
			.returnResult()
			.getResponseBody();

		// then
		assertThat(responsePost).isNotNull();
		assertThat(responsePost.getId()).isNotEmpty();
		assertThat(responsePost.getTitle()).isEqualTo(title);
		assertThat(responsePost.getContent()).isEqualTo(content);
	}

	private RequestPostDto requestPost(String title, String content) {
		return new RequestPostDto(title, content);
	}

	@DisplayName("게시글 목록을 조회한다")
	@Test
	void searchPosts() {
		// TODO : 2021/06/23 게시글을 3개 등록한다   -ksc
		// TODO : 2021/06/23 게시글 목록을 조회하여 3개가 맞는지 확인한다   -ksc
	}

	@DisplayName("게시글을 삭제한다")
	@Test
	void deletePost() {
		// TODO : 2021/06/23 게시글을 등록한다   -ksc
		// TODO : 2021/06/23 등록된 게시글을 삭제한다   -ksc
		// TODO : 2021/06/23 삭제되었는지 확인한다   -ksc
	}

	@DisplayName("게시글을 수정한다")
	@Test
	void updatePost() {
		// TODO : 2021/06/23 게시글을 등록한다   -ksc
		// TODO : 2021/06/23 등록된 게시글을 수정한다   -ksc
		// TODO : 2021/06/23 수정되었는지 확인한다   -ksc
	}
}
