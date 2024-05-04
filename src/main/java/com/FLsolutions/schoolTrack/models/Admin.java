package com.FLsolutions.schoolTrack.models;

import jakarta.persistence.*;

@Entity
@Table(name = "admins")
public class Admin extends User{
	
	private AdminRole adminRole;
	
	public Admin() {}

	public Admin(AdminRole adminRole) {
		super();
		this.adminRole = adminRole;
	}

	public AdminRole getAdminRole() {
		return adminRole;
	}

	public void setAdminRole(AdminRole adminRole) {
		this.adminRole = adminRole;
	}
	
	

}
