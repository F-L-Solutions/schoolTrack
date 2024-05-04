package com.FLsolutions.schoolTrack.models;

public enum AttendanceDay {
	MON("Monday"), TUE("Tuesday"), WED("Wednesday"), THU("Thursday"), FRI("Friday");

	private String name;

	AttendanceDay(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
