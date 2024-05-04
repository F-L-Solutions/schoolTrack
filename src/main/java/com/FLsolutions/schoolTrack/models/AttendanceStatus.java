package com.FLsolutions.schoolTrack.models;

public enum AttendanceStatus {
	ATTENDED("attended"), ABSENT("absent");
	
	private String name;

	AttendanceStatus(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
