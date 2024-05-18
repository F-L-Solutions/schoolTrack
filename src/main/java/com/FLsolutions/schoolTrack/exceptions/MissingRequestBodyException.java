package com.FLsolutions.schoolTrack.exceptions;

public class MissingRequestBodyException extends RuntimeException {
	public MissingRequestBodyException(String message) {
		super(message);
	}
}
