package com.FLsolutions.schoolTrack.dtos;

import com.FLsolutions.schoolTrack.models.Admin;
import com.FLsolutions.schoolTrack.models.Role;

public class AdminResponseDto implements ResponseDto {

	private final Long sysId;
	private final String firstName;
	private final String lastName;
	private final Role role;

	public AdminResponseDto(Admin admin) {
		this.sysId = admin.getSysId();
		this.firstName = admin.getFirstName();
		this.lastName = admin.getLastName();
		this.role = admin.getRole();
	}

	public AdminResponseDto(Long sysId, String firstName, String lastName) {
		this.sysId = sysId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.role = null;
	}

	public Long getSysId() {
		return sysId;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public Role getRole() {
		return role;
	}
}
