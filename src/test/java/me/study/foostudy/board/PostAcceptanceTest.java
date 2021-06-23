package me.study.foostudy.board;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import me.study.foostudy.AcceptanceTest;
import me.study.foostudy.board.domain.Post;

@DisplayName("게시글")
public class PostAcceptanceTest extends AcceptanceTest {

	@DisplayName("게시글을 등록한다")
	@Test
	void createPost() {
		게시글_등록_되어있음("새로운 게시글 제목", "새로운 게시글 내용을 등록합니다.");
	}

	private void 게시글_등록_되어있음(String title, String content) {
		client.post().uri("/posts")
			.bodyValue(getPost(title, content))
			.exchange()
			.expectStatus().isCreated()
			.expectBody();
	}

	private Post getPost(String title, String content) {
		return Post.builder()
			.title(title)
			.content(content)
			.build();
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
