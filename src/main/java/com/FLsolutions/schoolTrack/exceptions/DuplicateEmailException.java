package com.FLsolutions.schoolTrack.exceptions;

public class DuplicateEmailException extends GenericUserException {
    public DuplicateEmailException(String message) {
        super(message);
    }
}
