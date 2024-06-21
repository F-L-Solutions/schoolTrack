package com.FLsolutions.schoolTrack.exceptions;

import org.springframework.http.HttpStatus;

public class GenericReservationException extends GenericEventException {

	public GenericReservationException(String message, HttpStatus status) {
		super(message, status);
	}

}
