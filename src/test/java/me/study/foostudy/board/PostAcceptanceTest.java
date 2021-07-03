package me.study.foostudy.board;

import static me.study.foostudy.utils.DocumentationUtil.*;
import static org.assertj.core.api.Assertions.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.RequestFieldsSnippet;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import org.springframework.web.reactive.function.BodyInserters;

import me.study.foostudy.AcceptanceTest;
import me.study.foostudy.board.domain.Post;
import me.study.foostudy.board.dto.RequestPostDto;
import me.study.foostudy.board.dto.ResponsePostDto;
import reactor.core.publisher.Mono;

@DisplayName("게시글")
public class PostAcceptanceTest extends AcceptanceTest {

	@DisplayName("게시글을 등록한다")
	@Test
	void createPost() {
		게시글_등록_되어있음("새로운 게시글 제목", "새로운 게시글 내용을 등록합니다.");
	}

	@DisplayName("게시글 목록을 조회한다")
	@Test
	void searchPosts() {
		// given, when  게시글을 3개 등록한다
		게시글_등록_되어있음("새로운 게시글 제목 - 1", "새로운 게시글 내용을 등록합니다. - 1");
		게시글_등록_되어있음("새로운 게시글 제목 - 2", "새로운 게시글 내용을 등록합니다. - 2");
		게시글_등록_되어있음("새로운 게시글 제목 - 3", "새로운 게시글 내용을 등록합니다. - 3");

		// then  게시글 목록을 조회하여 3개가 맞는지 확인한다
		게시글_목록_조회(3);
	}

	private void 게시글_목록_조회(int size) {
		final List<ResponsePostDto> responsePosts = client.get().uri("/posts")
			.accept(APPLICATION_JSON)
			.exchange()
			.expectStatus().isOk()
			.expectBodyList(ResponsePostDto.class)
			.consumeWith(getDocument("post-list-item", getPostListResponseSnippet()))
			.returnResult()
			.getResponseBody();

		assertThat(responsePosts).isNotNull();
		assertThat(responsePosts.size()).isEqualTo(size);
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

	private void 게시글_등록_되어있음(String title, String content) {
		// given, when
		final ResponsePostDto responseDto = client.post().uri("/posts")
			.contentType(APPLICATION_JSON)
			.accept(APPLICATION_JSON)
			.body(BodyInserters.fromPublisher(Mono.just(requestPost(title, content)),
				RequestPostDto.class))
			.exchange()
			.expectStatus().isCreated()
			.expectBody(ResponsePostDto.class)
			.consumeWith(getDocument("post-new-item",
				getNewPostRequestSnippet(), getNewPostResponseSnippet()))
			.returnResult()
			.getResponseBody();

		// then
		assertThat(responseDto).isNotNull();
		assertThat(responseDto.getId()).isNotEmpty();
		assertThat(responseDto.getTitle()).isEqualTo(title);
		assertThat(responseDto.getContent()).isEqualTo(content);
		assertThat(responseDto.getCreatedDate()).isNotNull();
		assertThat(responseDto.getModifiedDate()).isNotNull();
	}

	private ResponseFieldsSnippet getNewPostResponseSnippet() {
		return responseFields(
			fieldWithPath("id").type(JsonFieldType.STRING).description("게시글 id"),
			fieldWithPath("title").type(JsonFieldType.STRING).description("게시글 제목"),
			fieldWithPath("content").type(JsonFieldType.STRING).description("게시글 내용"),
			fieldWithPath("createdDate").type(JsonFieldType.STRING).description("생성시간"),
			fieldWithPath("modifiedDate").type(JsonFieldType.STRING).description("수정시간")
		);
	}

	private RequestFieldsSnippet getNewPostRequestSnippet() {
		return requestFields(
			fieldWithPath("title").description("게시글 제목"),
			fieldWithPath("content").description("게시글 내용")
		);
	}

	private RequestPostDto requestPost(String title, String content) {
		return new RequestPostDto(title, content);
	}

	private ResponseFieldsSnippet getPostListResponseSnippet() {
		return responseFields(
			fieldWithPath("[]").description("array"),
			fieldWithPath("[].id").type(JsonFieldType.STRING).description("게시글 id"),
			fieldWithPath("[].title").type(JsonFieldType.STRING).description("게시글 제목"),
			fieldWithPath("[].content").type(JsonFieldType.STRING).description("게시글 내용"),
			fieldWithPath("[].createdDate").type(JsonFieldType.STRING).description("생성시간"),
			fieldWithPath("[].modifiedDate").type(JsonFieldType.STRING).description("수정시간")
		);
	}
}
