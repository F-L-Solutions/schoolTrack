package com.FLsolutions.schoolTrack.dtos;

import java.time.LocalDate;

import com.FLsolutions.schoolTrack.models.DayType;
import com.FLsolutions.schoolTrack.models.Event;

public class EventResponseDto implements ResponseDto {

	private final Long sysId;
	private final LocalDate date;
	private final int availableSpots;
	private final DayType dayType;

	public EventResponseDto(Event event) {
		this.sysId = event.getSysId();
		this.date = event.getDate();
		this.availableSpots = event.getAvailableSpots();
		this.dayType = event.getDayType();
	}

	public Long getSysId() {
		return sysId;
	}

	public LocalDate getDate() {
		return date;
	}

	public int getAvailableSpots() {
		return availableSpots;
	}

	public DayType getDayType() {
		return dayType;
	}

}
