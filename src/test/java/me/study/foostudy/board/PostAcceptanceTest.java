package me.study.foostudy.board;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import me.study.foostudy.AcceptanceTest;

@DisplayName("게시글")
public class PostAcceptanceTest extends AcceptanceTest {

	@DisplayName("게시글을 등록한다")
	@Test
	void createPost() {
		// TODO : 2021/06/23 게시글을 등록한다   -ksc
		// TODO : 2021/06/23 등록된 게시글을 확인한다   -ksc
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
