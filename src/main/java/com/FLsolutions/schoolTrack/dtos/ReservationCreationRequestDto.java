package com.FLsolutions.schoolTrack.dtos;

import java.time.LocalDate;

import com.FLsolutions.schoolTrack.models.DayType;
import com.FLsolutions.schoolTrack.models.ReservationStatus;

public class ReservationCreationRequestDto {

	private LocalDate date;
	private String kidUsername;
	private ReservationStatus status;
	private DayType dayType;

	public ReservationCreationRequestDto() {
	}

	public ReservationCreationRequestDto(LocalDate date, DayType dayType, String kidUsername) {
		this.date = date;
		this.dayType = dayType;
		this.kidUsername = kidUsername;
		this.status = ReservationStatus.WAITING;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getKidUsername() {
		return kidUsername;
	}

	public void setKidUsername(String kidUsername) {
		this.kidUsername = kidUsername;
	}

	public ReservationStatus getStatus() {
		return status;
	}

	public void setStatus(ReservationStatus status) {
		this.status = status;
	}

	public DayType getDayType() {
		return dayType;
	}

	public void setDayType(DayType dayType) {
		this.dayType = dayType;
	}

}
