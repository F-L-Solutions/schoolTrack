package com.FLsolutions.schoolTrack.dtos;

import java.time.LocalDate;

import com.FLsolutions.schoolTrack.models.Attendance;
import com.FLsolutions.schoolTrack.models.AttendanceDay;
import com.FLsolutions.schoolTrack.models.AttendanceStatus;
import com.FLsolutions.schoolTrack.models.DayType;
import com.FLsolutions.schoolTrack.models.Kid;

public class AttendanceResponseDto implements ResponseDto {

	private final Long sysId;
	private final LocalDate date;
	private final DayType dayType;
	private final Kid kid;
	private final AttendanceStatus attendanceStatus;
	private final AttendanceDay attendanceDay;
	private final boolean isExcused;

	public AttendanceResponseDto(Attendance attendance) {
		super();
		this.sysId = attendance.getSysId();
		this.date = attendance.getDate();
		this.dayType = attendance.getDayType();
		this.kid = attendance.getKid();
		this.attendanceStatus = attendance.getAttendanceStatus();
		this.attendanceDay = attendance.getAttendanceDay();
		this.isExcused = attendance.isExcused();
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

	public AttendanceStatus getAttendanceStatus() {
		return attendanceStatus;
	}

	public AttendanceDay getAttendanceDay() {
		return attendanceDay;
	}

	public boolean isExcused() {
		return isExcused;
	}

}
