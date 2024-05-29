package com.FLsolutions.schoolTrack.dtos;

import java.time.LocalDate;

import com.FLsolutions.schoolTrack.models.DayType;
import com.FLsolutions.schoolTrack.models.ReservationStatus;

public class ReservationCreationRequestDto {

	private LocalDate date;
	private String kidUserName;
	private ReservationStatus status;
	private DayType dayType;

	public ReservationCreationRequestDto() {
	}

	public ReservationCreationRequestDto(LocalDate date, DayType dayType, String kidUserName) {
		this.date = date;
		this.dayType = dayType;
		this.kidUserName = kidUserName;
		this.status = ReservationStatus.WAITING;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getKidUserName() {
		return kidUserName;
	}

	public void setKidUserName(String kidUserName) {
		this.kidUserName = kidUserName;
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
