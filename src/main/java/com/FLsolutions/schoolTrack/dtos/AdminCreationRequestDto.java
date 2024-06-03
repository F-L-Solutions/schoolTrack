package com.FLsolutions.schoolTrack.dtos;

import com.FLsolutions.schoolTrack.models.AdminRole;

public class AdminCreationRequestDto extends UserCreationRequestDto{
	
	private AdminRole adminRole;
	private String email;

	public AdminCreationRequestDto() {
		super();
		
	}

	public AdminCreationRequestDto(String firstName, String lastName, AdminRole adminRole, String email) {
		super(firstName, lastName);
		this.adminRole = adminRole;
		this.email = email;
		
	}

	public AdminRole getAdminRole() {
		return adminRole;
	}

	public void setAdminRole(AdminRole adminRole) {
		this.adminRole = adminRole;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
	

}
