package com.FLsolutions.schoolTrack.dtos;

import com.FLsolutions.schoolTrack.models.Role;

public class AdminCreationRequestDto extends UserCreationRequestDto {

	private Role role;
	private String email;

	public AdminCreationRequestDto() {
		super();
	}

	public AdminCreationRequestDto(String firstName, String lastName, Role role, String email) {
		super(firstName, lastName);
		this.role = role;
		this.email = email;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role adminRole) {
		this.role = adminRole;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
