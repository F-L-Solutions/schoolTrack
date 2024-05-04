package com.FLsolutions.schoolTrack.models;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "kids")
public class Kid extends User {

//	private List<Parent> parents;
//	private List<Reservation> reservations;
//	private List<SubstituteCredit> substitutes;
//	private List<Attendace> attendanceList;
	private DayType dayType;

	public Kid() {
		super();
	}

	public Kid(String firstName, String lastName, DayType dayType) {
		super(firstName, lastName);
		this.dayType = dayType;
	}

	public DayType getDayType() {
		return dayType;
	}

	public void setDayType(DayType dayType) {
		this.dayType = dayType;
	}

}