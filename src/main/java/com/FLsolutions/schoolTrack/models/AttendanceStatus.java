package com.FLsolutions.schoolTrack.models;

public enum AttendanceStatus {
	IDLE("idle"), //before the day of attendance
	ATTENDED("attended"), //after the day of attendance if attended
	ABSENT("absent"); //after the day of attendance if not attended

	private String name;

	AttendanceStatus(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
