package com.FLsolutions.schoolTrack.exceptions;

import org.springframework.http.HttpStatus;

public class DuplicateAttendanceException extends GenericEventException {

	public DuplicateAttendanceException(String message, HttpStatus status) {
		super(message, status);
	}

}
