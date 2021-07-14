package me.study.foostudy.board;

import static me.study.foostudy.utils.DocumentationUtil.*;
import static org.assertj.core.api.Assertions.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.RequestFieldsSnippet;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import org.springframework.restdocs.request.PathParametersSnippet;
import org.springframework.web.reactive.function.BodyInserters;

import me.study.foostudy.AcceptanceTest;
import me.study.foostudy.board.dto.RequestPostDto;
import me.study.foostudy.board.dto.RequestUpdatePostDto;
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
		// given  게시글을 3개 등록한다
		게시글_등록_되어있음("새로운 게시글 제목 - 1", "새로운 게시글 내용을 등록합니다. - 1");
		게시글_등록_되어있음("새로운 게시글 제목 - 2", "새로운 게시글 내용을 등록합니다. - 2");
		게시글_등록_되어있음("새로운 게시글 제목 - 3", "새로운 게시글 내용을 등록합니다. - 3");

		// when, then  게시글 목록을 조회하여 3개가 맞는지 확인한다
		게시글_목록_조회(3);
	}

	@DisplayName("특정 게시글을 id로 조회한다")
	@Test
	void searchPostById() {
		// given  게시글을 3개 등록한다
		final ResponsePostDto responsePostDto =
			게시글_등록_되어있음("새로운 게시글 제목 - 1", "새로운 게시글 내용을 등록합니다. - 1");
		게시글_등록_되어있음("새로운 게시글 제목 - 2", "새로운 게시글 내용을 등록합니다. - 2");
		게시글_등록_되어있음("새로운 게시글 제목 - 3", "새로운 게시글 내용을 등록합니다. - 3");

		// when, then
		게시글_상세조회(responsePostDto);
	}

	@DisplayName("게시글을 수정한다")
	@Test
	void updatePost() {
		// given
		final ResponsePostDto postDto = 게시글_등록_되어있음("새로운 게시글 제목 - 1", "새로운 게시글 내용을 등록합니다. - 1");

		// when, then
		게시글_수정_되어있음(postDto.getId(), "수정된 게시글 내용 등록");
	}

	@DisplayName("없는 게시글을 수정하려하면 예외를 발생시킨다")
	@Test
	void updatePost_invalidId() {
		게시글이_존재하지_않음("123");
	}

	@DisplayName("게시글을 삭제한다")
	@Test
	void deletePost() {
		// given
		final ResponsePostDto postDto = 게시글_등록_되어있음("새로운 게시글 제목 - 1", "새로운 게시글 내용을 등록합니다. - 1");
		final String postId = postDto.getId();

		// when
		게시글_삭제_되어있음(postId);

		// then
		게시글이_존재하지_않음(postId);
	}

	private void 게시글_삭제_되어있음(String postId) {
		client.delete().uri("/posts/{postId}", postId)
			.exchange()
			.expectStatus().isEqualTo(HttpStatus.NO_CONTENT)
			.expectBody()
			.consumeWith(getDocument("post-delete-item",
				getPathParameterWithPostId()));

	}

	private void 게시글_수정_되어있음(String postId, String updateContent) {
		final ResponsePostDto responseDto = client.patch().uri("/posts/{postId}", postId)
			.contentType(APPLICATION_JSON)
			.accept(APPLICATION_JSON)
			.body(BodyInserters.fromPublisher(Mono.just(requestUpdatePost(updateContent)),
				RequestUpdatePostDto.class))
			.exchange()
			.expectStatus().isOk()
			.expectBody(ResponsePostDto.class)
			.consumeWith(getDocument("post-update-item",
				getPathParameterWithPostId(),
				getUpdatePostRequestSnippet(), getPostResponseSnippet()))
			.returnResult()
			.getResponseBody();

		// then
		assertThat(responseDto).isNotNull();
		assertThat(responseDto.getId()).isNotEmpty();
		assertThat(responseDto.getContent()).isEqualTo(updateContent);
		assertThat(responseDto.getCreatedDate())
			.isNotEqualTo(responseDto.getModifiedDate());
	}

	private RequestFieldsSnippet getUpdatePostRequestSnippet() {
		return requestFields(
			fieldWithPath("updateContent").description("게시글 내용")
		);
	}

	private RequestUpdatePostDto requestUpdatePost(String updateContent) {
		return new RequestUpdatePostDto(updateContent);
	}

	private ResponsePostDto 게시글_등록_되어있음(String title, String content) {
		// given, when
		final ResponsePostDto responseDto = 게시글_등록(title, content);

		// then
		assertThat(responseDto).isNotNull();
		assertThat(responseDto.getId()).isNotEmpty();
		assertThat(responseDto.getTitle()).isEqualTo(title);
		assertThat(responseDto.getContent()).isEqualTo(content);
		assertThat(responseDto.getCreatedDate()).isNotNull();
		assertThat(responseDto.getModifiedDate()).isNotNull();

		return responseDto;
	}

	private ResponsePostDto 게시글_등록(String title, String content) {
		return client.post().uri("/posts")
			.contentType(APPLICATION_JSON)
			.accept(APPLICATION_JSON)
			.body(BodyInserters.fromPublisher(Mono.just(requestPost(title, content)),
				RequestPostDto.class))
			.exchange()
			.expectStatus().isCreated()
			.expectBody(ResponsePostDto.class)
			.consumeWith(getDocument("post-new-item",
				getNewPostRequestSnippet(), getPostResponseSnippet()))
			.returnResult()
			.getResponseBody();
	}

	private ResponseFieldsSnippet getPostResponseSnippet() {
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

	private void 게시글_상세조회(ResponsePostDto postDto) {
		final ResponsePostDto responsePostDto = client.get().uri("/posts/{postId}", postDto.getId())
			.accept(APPLICATION_JSON)
			.exchange()
			.expectStatus().isOk()
			.expectBody(ResponsePostDto.class)
			.consumeWith(getDocument("post-detail-item",
				getPathParameterWithPostId(),
				getPostResponseSnippet()))
			.returnResult()
			.getResponseBody();

		assertThat(responsePostDto).isNotNull();
		assertThat(responsePostDto.getId()).isEqualTo(postDto.getId());
		assertThat(responsePostDto.getTitle()).isEqualTo(postDto.getTitle());
		assertThat(responsePostDto.getContent()).isEqualTo(postDto.getContent());
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

	private void 게시글이_존재하지_않음(String postId) {
		final String errorMessage = client.patch().uri("/posts/" + postId)
			.contentType(APPLICATION_JSON)
			.accept(APPLICATION_JSON)
			.body(BodyInserters.fromPublisher(Mono.just(requestUpdatePost("수정된 게시글 내용 등록")),
				RequestUpdatePostDto.class))
			.exchange()
			.expectStatus().isBadRequest()
			.expectBody(String.class)
			.returnResult()
			.getResponseBody();

		assertThat(errorMessage).isEqualTo("잘못된 post id");
	}

	private PathParametersSnippet getPathParameterWithPostId() {
		return pathParameters(parameterWithName("postId").description("게시글 id"));
	}
}
