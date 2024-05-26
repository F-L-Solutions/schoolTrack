package com.FLsolutions.schoolTrack.exceptions;

import org.springframework.http.HttpStatus;

public class GenericEventException extends RuntimeException {

	private final HttpStatus status;

	public GenericEventException(String message, HttpStatus status) {
		super(message);
		this.status = status;
	}

	public HttpStatus getStatus() {
		return status;
	}

}
