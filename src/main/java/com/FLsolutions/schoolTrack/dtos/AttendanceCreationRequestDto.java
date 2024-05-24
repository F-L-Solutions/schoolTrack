package com.FLsolutions.schoolTrack.dtos;

import java.time.LocalDate;

import com.FLsolutions.schoolTrack.models.AttendanceDay;
import com.FLsolutions.schoolTrack.models.AttendanceStatus;
import com.FLsolutions.schoolTrack.models.DayType;
import com.FLsolutions.schoolTrack.models.Kid;

public class AttendanceCreationRequestDto {

	private LocalDate date;
	private LocalDate startDate;
	private LocalDate endDate;
	private DayType dayType;
	private Kid kid;
	private AttendanceStatus status;
	private AttendanceDay attendanceDay;

	public AttendanceCreationRequestDto() {
	}

	// for bulk creation, e.g. every Thursdays the same attendance
	public AttendanceCreationRequestDto(LocalDate startDate, LocalDate endDate, DayType dayType, Kid kid,
			AttendanceStatus status, AttendanceDay attendanceDay) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.dayType = dayType;
		this.kid = kid;
		this.status = AttendanceStatus.IDLE;
		this.attendanceDay = attendanceDay;
	}

	// for singular attendance creation
	public AttendanceCreationRequestDto(LocalDate date, DayType dayType, Kid kid, AttendanceStatus status) {
		this.date = date;
		this.dayType = dayType;
		this.kid = kid;
		this.status = AttendanceStatus.IDLE;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
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

	public DayType getDayType() {
		return dayType;
	}

	public void setDayType(DayType dayType) {
		this.dayType = dayType;
	}

	public Kid getKid() {
		return kid;
	}

	public void setKid(Kid kid) {
		this.kid = kid;
	}

	public AttendanceStatus getStatus() {
		return status;
	}

	public void setStatus(AttendanceStatus status) {
		this.status = status;
	}

	public AttendanceDay getAttendanceDay() {
		return attendanceDay;
	}

	public void setAttendanceDay(AttendanceDay attendanceDay) {
		this.attendanceDay = attendanceDay;
	}
}
