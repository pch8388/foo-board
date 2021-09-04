package me.study.foostudy.board.exception;

import me.study.foostudy.common.exception.BusinessException;

public class PostPermissionException extends BusinessException {
	public PostPermissionException() {
		super("다른 유저의 게시글을 수정할 수 없습니다");
	}
}
