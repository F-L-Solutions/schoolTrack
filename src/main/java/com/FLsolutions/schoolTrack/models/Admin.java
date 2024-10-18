package com.FLsolutions.schoolTrack.models;

import jakarta.persistence.*;

@Entity
@Table(name = "admins")
public class Admin extends User{
	
	private Role role;
	
	public Admin() {}

	public Admin(String firstName, String lastName, String email, Role role) {
		super(firstName, lastName, email);
		this.role = role;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
}
