package com.mercadolivre.cliente_service.handler;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Getter
@Log4j2

public class ApiException extends RuntimeException {

	private final HttpStatus statusException;

	private final ErrorApiResponse bodyException;

	private static final long serialVersionUID = 1L;

	private ApiException(HttpStatus statusException, String message, Exception e) {
		super(message, e);
		this.statusException = statusException;
		this.bodyException = ErrorApiResponse.builder().message(message).description(getDescription(e)).build();
	}

	public static ApiException build(HttpStatus statusException, String message) {
		return new ApiException(statusException, message, null);
	}

	public static ApiException build(HttpStatus statusException, String message, Exception e) {
		log.error("Exception lançada: ", e);
		return new ApiException(statusException, message, e);
	}

	private String getDescription(Exception e) {
		return Optional.ofNullable(e).map(ApiException::getMessageCause).orElse(null);
	}

	private static String getMessageCause(Exception e) {
		return e.getCause() != null ? e.getCause().getMessage() : e.getMessage();
	}

	public ResponseEntity<ErrorApiResponse> buildErrorResponseEntity() {
		return ResponseEntity.status(statusException).body(bodyException);
	}

}
