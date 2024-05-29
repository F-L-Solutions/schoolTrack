package com.FLsolutions.schoolTrack.exceptions;

import org.springframework.http.HttpStatus;

public class DuplicateReservationException extends GenericEventException {

	public DuplicateReservationException(String message, HttpStatus status) {
		super(message, status);
	}
}
