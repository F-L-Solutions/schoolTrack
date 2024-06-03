package com.FLsolutions.schoolTrack.models;

public enum AttendanceStatus {
	IDLE("idle"), // before the day of attendance
	ATTENDED("attended"), // after the day of attendance if attended
	ABSENT("absent"), // after the day of attendance if not attended
	CANCELED_ON_TIME("canceled_on_time"), // when attendance is successfully cancelled at least 24 hours before the day of attendance
	CANCELED_LATE("canceled_late"); // when attendance is cancelled less than 24 hours before the day of attendance

	private String name;

	AttendanceStatus(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
