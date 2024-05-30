package com.FLsolutions.schoolTrack.dtos;

import java.util.List;

import com.FLsolutions.schoolTrack.models.Kid;
import com.FLsolutions.schoolTrack.models.Parent;

public class ParentResponseDto implements ResponseDto {
	
	private final Long sysId;
	private final String firstName;
	private final String lastName;
	private final List<Kid> kids;
	
	public ParentResponseDto(Parent parent) {
		this.sysId = parent.getSysId();
		this.firstName = parent.getFirstName();
		this.lastName = parent.getLastName();
		this.kids = parent.getKids();
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

	public List<Kid> getKids() {
		return kids;
	}
	
	

}
