package com.FLsolutions.schoolTrack.exceptions;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;

public class DuplicateEventException extends GenericEventException {

	private final List<LocalDate> existingDates;

	public DuplicateEventException(List<LocalDate> existingDates, HttpStatus status) {
		super("Bulk creation of events failed. Events already exist for these specified dates: " + existingDates,
				status);
		this.existingDates = existingDates;
	}

	public List<LocalDate> getExistingDates() {
		return existingDates;
	}
}
