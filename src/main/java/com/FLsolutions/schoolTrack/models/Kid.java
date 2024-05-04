package com.FLsolutions.schoolTrack.models;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "kids")
public class Kid extends User {

	@ElementCollection
	@Enumerated(EnumType.STRING)
	private List<AttendanceDay> attendanceDay;
	private int substitutes;
	private DayType dayType;

	public Kid() {
		super();
	}

	public Kid(String firstName, String lastName, DayType dayType, List<AttendanceDay> attendanceDay) {
		super(firstName, lastName);
		this.substitutes = 0;
		this.attendanceDay = attendanceDay;
		this.dayType = dayType;
	}

	public List<AttendanceDay> getAttendance() {
		return attendanceDay;
	}

	public void setAttendance(List<AttendanceDay> attendanceDay) {
		this.attendanceDay = attendanceDay;
	}

	public int getSubstitutes() {
		return substitutes;
	}

	public void setSubstitutes(int substitutes) {
		this.substitutes = substitutes;
	}

	public DayType getDayType() {
		return dayType;
	}

	public void setDayType(DayType dayType) {
		this.dayType = dayType;
	}

}