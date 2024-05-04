package com.FLsolutions.schoolTrack.models;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table (name= "reservations")
public class Reservation extends Event{
	
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kid_id")
    private Kid kid; 
	
	@Enumerated (EnumType.STRING)
	private ReservationStatus status;
	
	public Reservation() {
		super();
		this.status = ReservationStatus.WAITING;
	}
	
    public Reservation(LocalDate date, DayType dayType, Kid kid) { 
        super(date, dayType); 
        this.kid = kid;
        this.status = ReservationStatus.WAITING;
    }
	
    public Kid getKid() {
        return kid;
    } 
	
	public ReservationStatus getStatus() {
		return status;
	}

    public void setKid(Kid kid) {
        this.kid = kid;
    } 

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

}
