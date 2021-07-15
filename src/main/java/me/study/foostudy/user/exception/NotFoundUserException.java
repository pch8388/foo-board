package me.study.foostudy.user.exception;

import me.study.foostudy.common.exception.BusinessException;

public class NotFoundUserException extends BusinessException {

	private static final String MESSAGE_FORMAT = "%s 라는 이름의 유저가 없습니다.";
	public NotFoundUserException(String username) {
		super(String.format(MESSAGE_FORMAT, username));
	}
}
