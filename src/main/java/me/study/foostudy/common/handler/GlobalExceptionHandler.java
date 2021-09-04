package me.study.foostudy.common.handler;

import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

import me.study.foostudy.board.exception.PostPermissionException;
import me.study.foostudy.common.exception.BusinessException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(IllegalArgumentException.class)
	protected ResponseEntity<?> handleIllegalArgumentException(final IllegalArgumentException e) {
		return ResponseEntity.badRequest().body(e.getMessage());
	}

	@ExceptionHandler(PostPermissionException.class)
	public ResponseEntity<?> handleAuthenticationException(final BusinessException e) {
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
	}

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<?> handleBusinessException(final BusinessException e) {
		// FIXME: 2021/07/15 STATUS 와 BODY 만드는 부분 공통화 수정 필요  -ksc
		return ResponseEntity.badRequest().body(e.getMessage());
	}

	@ExceptionHandler(WebExchangeBindException.class)
	public ResponseEntity<?> handleMethodArgumentNotValidException(final WebExchangeBindException e) {
		String messages = e.getFieldErrors()
			.stream()
			.map(FieldError::getDefaultMessage)
			.filter(Objects::nonNull)
			.map(Object::toString)
			.collect(Collectors.joining(", "));
		return ResponseEntity.badRequest().body(messages);
	}
}