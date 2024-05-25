package com.FLsolutions.schoolTrack.exceptions;

import org.springframework.http.HttpStatus;

public class DuplicateEmailException extends GenericUserException {
    public DuplicateEmailException(String message, HttpStatus status) {
        super(message, status);
    }
}
