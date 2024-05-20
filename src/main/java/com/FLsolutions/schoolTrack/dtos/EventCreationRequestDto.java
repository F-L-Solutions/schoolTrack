package com.FLsolutions.schoolTrack.dtos;

import com.FLsolutions.schoolTrack.models.DayType;

public class EventCreationRequestDto {

	private int availableSpots;
	private DayType dayType;
	private int numberOfEvents;
	
	public EventCreationRequestDto() {
		
	}

	public EventCreationRequestDto(int availableSpots, DayType dayType, int numberOfEvents) {
		this.availableSpots = availableSpots;
		this.dayType = dayType;
		this.numberOfEvents = numberOfEvents;
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

	public int getNumberOfEvents() {
		return numberOfEvents;
	}

	public void setNumberOfEvents(int numberOfEvents) {
		this.numberOfEvents = numberOfEvents;
	}
}
