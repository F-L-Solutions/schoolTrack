package com.FLsolutions.schoolTrack.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;

@Entity
@Table(name = "events")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Event {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private long sysId;

	private LocalDate date;

	@Column(name = "available_spots")
	private int availableSpots;

	@Enumerated(EnumType.STRING)
	@Column(name = "day_type")
	private DayType dayType;

	@CreationTimestamp
	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	public Event() {

	}

	public Event(LocalDate date, int availableSpots, DayType dayType) {
		this.date = date;
		this.availableSpots = availableSpots;
		this.dayType = dayType;
	}

	public Event(LocalDate date, DayType dayType) {
		this.date = date;
		this.dayType = dayType;
	}

	
	public long getSysId() {
		return sysId;
	}

	public DayType getDayType() {
		return dayType;
	}

	public int getAvailableSpots() {
		return availableSpots;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDayType(DayType dayType) {
		this.dayType = dayType;
	}

	public void setAvailableSpots(int availableSpots) {
		this.availableSpots = availableSpots;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}
}
