package com.FLsolutions.schoolTrack.exceptions;

import java.time.LocalDate;
import java.util.List;

public class DuplicateEventException extends GenericEventException {

	private final List<LocalDate> existingDates;

	public DuplicateEventException(List<LocalDate> existingDates) {
		super("Bulk creation of events failed. Events already exist for these specified dates: " + existingDates);
		this.existingDates = existingDates;
	}

	public List<LocalDate> getExistingDates() {
		return existingDates;
	}
}
