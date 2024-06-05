package com.FLsolutions.schoolTrack.dtos;

import com.FLsolutions.schoolTrack.models.Admin;
import com.FLsolutions.schoolTrack.models.AdminRole;

public class AdminResponseDto implements ResponseDto{
	
	private final Long sysId;
	private final String firstName;
	private final String lastName;
	private final AdminRole adminRole;
	
	public AdminResponseDto(Admin admin) {
		this.sysId = admin.getSysId();
		this.firstName = admin.getFirstName();
		this.lastName = admin.getLastName();
		this.adminRole = admin.getAdminRole();
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

	public AdminRole getAdminRole() {
		return adminRole;
	}
	
	

}
