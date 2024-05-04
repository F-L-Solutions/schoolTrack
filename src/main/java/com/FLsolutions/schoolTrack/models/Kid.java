package com.FLsolutions.schoolTrack.models;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "kids")
public class Kid extends User {

//	private List<Parent> parents;
	@OneToMany(mappedBy = "kid", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<Reservation> reservations;
	
//	private List<SubstituteCredit> substitutes;
	
	@OneToMany(mappedBy = "kid", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<Attendance> attendanceList;
	
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
	
    public List<Reservation> getReservations() {
        return reservations;
    }

	public void setDayType(DayType dayType) {
		this.dayType = dayType;
	}
	
    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

}