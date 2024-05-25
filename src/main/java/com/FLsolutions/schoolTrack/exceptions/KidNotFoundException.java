package com.FLsolutions.schoolTrack.exceptions;

import org.springframework.http.HttpStatus;

public class KidNotFoundException extends GenericUserException {

	public KidNotFoundException(String message, HttpStatus status) {
		super(message, status);
	}

}
