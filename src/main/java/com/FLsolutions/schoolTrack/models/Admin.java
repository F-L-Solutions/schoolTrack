package com.FLsolutions.schoolTrack.models;

import jakarta.persistence.*;

@Entity
@Table(name = "admins")
public class Admin extends User{
	
	private AdminRole adminRole;
	
	public Admin() {}

	public Admin(String firstName, String lastName, String email, AdminRole adminRole) {
		super(firstName, lastName, email);
		this.adminRole = adminRole;
	}

	public AdminRole getAdminRole() {
		return adminRole;
	}

	public void setAdminRole(AdminRole adminRole) {
		this.adminRole = adminRole;
	}
	
	

}
