package com.FLsolutions.schoolTrack.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "parents")
public class Parent extends User {

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "parent_kid", joinColumns = @JoinColumn(name = "parent_id"), inverseJoinColumns = @JoinColumn(name = "kid_id"))
	private List<Kid> kids = new ArrayList<Kid>();

	public Parent() {
	}
	
	public Parent(String firstName, String lastName, String email, String telNumber) {
		super(firstName, lastName, email, telNumber);
	}

	public Parent(List<Kid> kids) {
		super();
		this.kids = kids;
	}

	public List<Kid> getKids() {
		return kids;
	}

	public void setKids(List<Kid> kids) {
		this.kids = kids;
	}

}
