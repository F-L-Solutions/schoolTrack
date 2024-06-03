package com.FLsolutions.schoolTrack.exceptions;

import org.springframework.http.HttpStatus;

public class GenericAttendanceException extends GenericEventException {

	public GenericAttendanceException(String message, HttpStatus status) {
		super(message, status);
	}

}
