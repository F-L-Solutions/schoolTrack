package com.FLsolutions.schoolTrack.exceptions;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.FLsolutions.schoolTrack.dtos.ErrorResponseDto;

@RestControllerAdvice
public class ControllerExceptionHandler {

	public ControllerExceptionHandler() {
	}

	@ExceptionHandler(MissingRequestBodyException.class)
	public ResponseEntity<ErrorResponseDto> handleMissingRequestBodyException(MissingRequestBodyException ex) {
		List<String> details = new ArrayList<>();
		details.add(ex.getMessage());
		
		ErrorResponseDto errorResponse = new ErrorResponseDto("Validation Failed", details,
				HttpStatus.CONFLICT.value());
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(GenericUserException.class)
	public ResponseEntity<ErrorResponseDto> handleGenericUserException(GenericUserException ex) {
		List<String> details = new ArrayList<>();
		details.add(ex.getMessage());
		
		ErrorResponseDto errorResponse = new ErrorResponseDto("Validation Failed", details,
				ex.getStatus().value());
		return new ResponseEntity<>(errorResponse, ex.getStatus());
	}
	
	@ExceptionHandler(GenericEventException.class)
	public ResponseEntity<ErrorResponseDto> handleGenericEventException(GenericEventException ex) {
		List<String> details = new ArrayList<>();
		details.add(ex.getMessage());
		
		ErrorResponseDto errorResponse = new ErrorResponseDto("Validation Failed", details,
				ex.getStatus().value());
		return new ResponseEntity<>(errorResponse, ex.getStatus());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponseDto> handleValidationException(MethodArgumentNotValidException ex) {
		List<String> details = ex.getBindingResult().getAllErrors().stream()
				.map(error -> ((FieldError) error).getField() + ": " + error.getDefaultMessage())
				.collect(Collectors.toList());
		System.out.println(details);

		ErrorResponseDto errorResponse = new ErrorResponseDto("Validation Failed, MethodArgumentNotValidException", details,
				HttpStatus.BAD_REQUEST.value());

		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ErrorResponseDto> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
		List<String> details = new ArrayList<>();
		details.add(ex.getMessage());
		
		ErrorResponseDto errorResponse = new ErrorResponseDto("Validation Failed, HttpMessageNotReadableException", details,
				HttpStatus.CONFLICT.value());
		return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
	}
}
