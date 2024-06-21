package com.FLsolutions.schoolTrack.dtos;

import java.time.LocalDate;

import com.FLsolutions.schoolTrack.models.DayType;
import com.FLsolutions.schoolTrack.models.Kid;
import com.FLsolutions.schoolTrack.models.Reservation;
import com.FLsolutions.schoolTrack.models.ReservationStatus;

public class ReservationResponseDto implements ResponseDto {

	private final Long sysId;
	private final LocalDate date;
	private final DayType dayType;
	private final Kid kid;
	private final ReservationStatus status;

	public ReservationResponseDto(Reservation reservation) {
		this.sysId = reservation.getSysId();
		this.date = reservation.getDate();
		this.dayType = reservation.getDayType();
		this.kid = reservation.getKid();
		this.status = reservation.getStatus();
	}

	public Long getSysId() {
		return sysId;
	}

	public LocalDate getDate() {
		return date;
	}

	public DayType getDayType() {
		return dayType;
	}

	public KidResponseDto getKid() {
		KidResponseDto kidResponseDto = new KidResponseDto(kid);
		return kidResponseDto;
	}

	public ReservationStatus getStatus() {
		return status;
	}

}
