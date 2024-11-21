package com.FLsolutions.schoolTrack.models;

import jakarta.persistence.*;

@Entity
@Table(name = "admins")
public class Admin extends User {
	
	public Admin() {
	}

	public Admin(String firstName, String lastName, String email, Role role) {
		super(firstName, lastName, email);
	}

}
