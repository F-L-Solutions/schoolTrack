package com.FLsolutions.schoolTrack.models;

public enum ReservationStatus {
	WAITING("waiting"), //when reservation was created and user waits for the spot
	CONFIRMED("confirmed"), //when a spot got free, reservation is confirmed
	CANCELLED("cancelled"), // when user cancels reservation
	REJECTED("rejected"); // when system rejects reservation, e.g. time runs out at 8PM day before the event
	
	private String name;
	
	ReservationStatus(String name){
		this.name= name;
	}
	
	public String getName() {
		return name;
	}

}
