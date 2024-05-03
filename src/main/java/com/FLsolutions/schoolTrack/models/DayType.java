package com.FLsolutions.schoolTrack.models;

public enum DayType {
	HALF_DAY("half"), FULL_DAY("full");

	private String name;

	DayType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
