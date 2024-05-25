package com.FLsolutions.schoolTrack.exceptions;

import org.springframework.http.HttpStatus;

public class GenericUserException extends RuntimeException {
	
	private final HttpStatus status;
	
    public GenericUserException(String message, HttpStatus status) {
        super(message);
        this.status= status;
    }

	public HttpStatus getStatus() {
		return status;
	}
}