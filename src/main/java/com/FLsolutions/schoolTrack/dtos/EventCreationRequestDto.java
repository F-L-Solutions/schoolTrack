package com.FLsolutions.schoolTrack.dtos;

import java.time.LocalDate;

import com.FLsolutions.schoolTrack.models.DayType;

public class EventCreationRequestDto {

	private int availableSpots;
	private DayType dayType;
	private LocalDate startDate;
	private LocalDate endDate;
	
	public EventCreationRequestDto() {
		
	}

	public EventCreationRequestDto(int availableSpots, DayType dayType, int numberOfEvents, LocalDate startDate, LocalDate endDate) {
		this.availableSpots = availableSpots;
		this.dayType = dayType;
		this.startDate= startDate;
		this.endDate= endDate;
	}

	public int getAvailableSpots() {
		return availableSpots;
	}

	public void setAvailableSpots(int availableSpots) {
		this.availableSpots = availableSpots;
	}

	public DayType getDayType() {
		return dayType;
	}

	public void setDayType(DayType dayType) {
		this.dayType = dayType;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
}
