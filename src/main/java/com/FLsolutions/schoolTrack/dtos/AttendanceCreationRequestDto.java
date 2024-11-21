package com.FLsolutions.schoolTrack.dtos;

import java.time.LocalDate;
import java.util.List;

import com.FLsolutions.schoolTrack.models.AttendanceDay;
import com.FLsolutions.schoolTrack.models.AttendanceStatus;
import com.FLsolutions.schoolTrack.models.DayType;

public class AttendanceCreationRequestDto {

	private LocalDate date;
	private LocalDate startDate;
	private LocalDate endDate;
	private DayType dayType;
	private String kidUsername;
	private AttendanceStatus status;
	private List<AttendanceDay> attendanceDays;

	public AttendanceCreationRequestDto() {
	}

	// for bulk creation, e.g. every Thursdays the same attendance
	public AttendanceCreationRequestDto(LocalDate startDate, LocalDate endDate, DayType dayType, String kidUsername,
			List<AttendanceDay> attendanceDays) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.dayType = dayType;
		this.kidUsername = kidUsername;
		this.status = AttendanceStatus.IDLE;
		this.attendanceDays = attendanceDays;
	}

	// for singular attendance creation
	public AttendanceCreationRequestDto(LocalDate date, DayType dayType, String kidUsername) {
		this.date = date;
		this.dayType = dayType;
		this.kidUsername = kidUsername;
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

	public String getKidUsername() {
		return kidUsername;
	}

	public void setKidUsername(String kidUsername) {
		this.kidUsername = kidUsername;
	}

	public AttendanceStatus getStatus() {
		return status;
	}

	public void setStatus(AttendanceStatus status) {
		this.status = status;
	}

	public List<AttendanceDay> getAttendanceDays() {
		return attendanceDays;
	}

	public void setAttendanceDays(List<AttendanceDay> attendanceDays) {
		this.attendanceDays = attendanceDays;
	}

}
