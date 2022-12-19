package com.example.projekat.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.PAYLOAD_TOO_LARGE)
public class TooLargeException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public TooLargeException(String message) {
		super(message);
	}
}
