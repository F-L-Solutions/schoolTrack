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
	private String kidUserName;
	private AttendanceStatus status;
	private AttendanceDay attendanceDay;

	public AttendanceCreationRequestDto() {
	}

	// for bulk creation, e.g. every Thursdays the same attendance
	public AttendanceCreationRequestDto(LocalDate startDate, LocalDate endDate, DayType dayType, String kidUserName,
			AttendanceDay attendanceDay) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.dayType = dayType;
		this.kidUserName = kidUserName;
		this.status = AttendanceStatus.IDLE;
		this.attendanceDay = attendanceDay;
	}

	// for singular attendance creation
	public AttendanceCreationRequestDto(LocalDate date, DayType dayType, String kidUserName) {
		this.date = date;
		this.dayType = dayType;
		this.kidUserName = kidUserName;
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

	public String getKidUserName() {
		return kidUserName;
	}

	public void setKidUserName(String kidUserName) {
		this.kidUserName = kidUserName;
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
