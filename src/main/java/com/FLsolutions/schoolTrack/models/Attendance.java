package com.FLsolutions.schoolTrack.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "attendances")
public class Attendance extends Event {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "kid_id")
	private Kid kid;

	@Column(name= "attendance_status", length = 25)
	@Enumerated(EnumType.STRING)
	private AttendanceStatus attendanceStatus;

	@Column(name= "attendance_day")
	@Enumerated(EnumType.STRING)
	private AttendanceDay attendanceDay;

	@Column(name= "is_excused")
	private boolean isExcused;

	public Attendance() {
		super();
	}

	public Attendance(LocalDate date, DayType dayType, Kid kid, AttendanceStatus attendanceStatus,
			AttendanceDay attendanceDay) {
		super(date, dayType);
		this.kid = kid;
		this.attendanceStatus = attendanceStatus;
		this.attendanceDay = attendanceDay;
		this.isExcused = false;
	}

	public Attendance(LocalDate date, DayType dayType, Kid kid, AttendanceDay attendanceDay) {
		super(date, dayType);
		this.kid = kid;
		this.attendanceStatus = AttendanceStatus.IDLE;
		this.attendanceDay = attendanceDay;
		this.isExcused = false;
	}

	public Attendance(LocalDate date, DayType dayType, Kid kid) {
		super(date, dayType);
		this.kid = kid;
		this.attendanceStatus = AttendanceStatus.IDLE;
		this.isExcused = false;
	}

	public boolean isCancelableOnTime() {
		return LocalDateTime.now().isBefore(this.getDate().atStartOfDay().minusHours(24));
	}
	
	public boolean isCancelable() {
		return LocalDateTime.now().isBefore(this.getDate().atStartOfDay());
	}
	
	public boolean isAlreadyCanceled() {
		return this.attendanceStatus == AttendanceStatus.CANCELED_LATE || this.attendanceStatus == AttendanceStatus.CANCELED_ON_TIME;
	}

	public Kid getKid() {
		return kid;
	}

	public void setKid(Kid kid) {
		this.kid = kid;
	}

	public AttendanceStatus getAttendanceStatus() {
		return attendanceStatus;
	}

	public void setAttendanceStatus(AttendanceStatus attendanceStatus) {
		this.attendanceStatus = attendanceStatus;
	}

	public AttendanceDay getAttendanceDay() {
		return attendanceDay;
	}

	public void setAttendanceDay(AttendanceDay attendanceDay) {
		this.attendanceDay = attendanceDay;
	}

	public boolean isExcused() {
		return isExcused;
	}

	public void setExcused(boolean isExcused) {
		this.isExcused = isExcused;
	}

}
