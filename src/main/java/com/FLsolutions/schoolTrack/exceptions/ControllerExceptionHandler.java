package com.FLsolutions.schoolTrack.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.FLsolutions.schoolTrack.dtos.ErrorResponseDto;

@RestControllerAdvice
public class ControllerExceptionHandler {

	public ControllerExceptionHandler() {
	}

	@ExceptionHandler(MissingRequestBodyException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ErrorResponseDto emptyRequestBody() {
		return new ErrorResponseDto("Part of request body is missing");
	}
}
