package com.FLsolutions.schoolTrack.models;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "kids")
public class Kid extends User {

	@ManyToMany(mappedBy = "kids")
	private List<Parent> parents;
	
	@OneToMany(mappedBy = "kid", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<Reservation> reservations;
	
	@OneToMany(mappedBy = "kid", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<SubstituteCredit> substitutes;
	
	@OneToMany(mappedBy = "kid", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<Attendance> attendanceList;
	
	private DayType dayType;

	public Kid() {
		super();
	}

	public Kid(String firstName, String lastName, DayType dayType, List<Parent> parents) {
		super(firstName, lastName);
		this.dayType = dayType;
		this.parents = parents;
	}

	public DayType getDayType() {
		return dayType;
	}

	public void setDayType(DayType dayType) {
		this.dayType = dayType;
	}
	
    public List<Reservation> getReservations() {
        return reservations;
    }
	
    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

	public List<Parent> getParents() {
		return parents;
	}

	public void setParents(List<Parent> parents) {
		this.parents = parents;
	}
    

}