package com.FLsolutions.schoolTrack.models;

import java.time.DayOfWeek;

public enum AttendanceDay {
	MON("Monday", DayOfWeek.MONDAY), TUE("Tuesday", DayOfWeek.TUESDAY), WED("Wednesday", DayOfWeek.WEDNESDAY),
	THU("Thursday", DayOfWeek.THURSDAY), FRI("Friday", DayOfWeek.FRIDAY);

	private String name;
	private DayOfWeek dayOfWeek;

	AttendanceDay(String name, DayOfWeek dayOfWeek) {
		this.name = name;
		this.dayOfWeek = dayOfWeek;
	}

	public String getName() {
		return name;
	}

	public DayOfWeek getDayOfWeek() {
		return dayOfWeek;
	}

}
