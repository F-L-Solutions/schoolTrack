package com.FLsolutions.schoolTrack.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.FLsolutions.schoolTrack.services.Utils;

@Entity
@Table(name = "kids")
public class Kid {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long sysId;
	
	private String firstName;
	
	private String lastName;
	
	@Column(name = "user_name", unique = true, nullable = false)
	private String username;
	
	@ManyToMany(mappedBy = "kids")
	private List<Parent> parents = new ArrayList<Parent>();
	
	@OneToMany(mappedBy = "kid", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<Reservation> reservations;
	
	@OneToMany(mappedBy = "kid", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<SubstituteCredit> substitutes;
	
	@OneToMany(mappedBy = "kid", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<Attendance> attendanceList;
	
	private DayType dayType;
	
	@CreationTimestamp
	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	public Kid() {
		super();
	}

	public Kid(String firstName, String lastName, List<Parent> parents) {
		this.firstName= firstName;
		this.lastName= lastName;
		this.username = Utils.createUserName(firstName, lastName);
		this.parents = parents;
	}
	
//	public Kid(String firstName, String lastName, DayType dayType, List<Parent> parents) {
//		super(firstName, lastName);
//		this.dayType = dayType;
//		this.parents = parents;
//	}
	
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

	public Long getSysId() {
		return sysId;
	}

	public void setSysId(Long sysId) {
		this.sysId = sysId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public List<SubstituteCredit> getSubstitutes() {
		return substitutes;
	}

	public void setSubstitutes(List<SubstituteCredit> substitutes) {
		this.substitutes = substitutes;
	}

	public List<Attendance> getAttendanceList() {
		return attendanceList;
	}

	public void setAttendanceList(List<Attendance> attendanceList) {
		this.attendanceList = attendanceList;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
}