package me.study.foostudy.common.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(IllegalArgumentException.class)
	protected ResponseEntity<?> handleIllegalArgumentException(final IllegalArgumentException e) {
		return ResponseEntity.badRequest().body(e.getMessage());
	}
}