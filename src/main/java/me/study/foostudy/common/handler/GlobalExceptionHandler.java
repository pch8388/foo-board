package me.study.foostudy.common.handler;

import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(IllegalArgumentException.class)
	protected ResponseEntity<?> handleIllegalArgumentException(final IllegalArgumentException e) {
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