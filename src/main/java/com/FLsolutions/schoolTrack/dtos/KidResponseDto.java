package com.FLsolutions.schoolTrack.dtos;

import java.util.ArrayList;
import java.util.List;

import com.FLsolutions.schoolTrack.models.DayType;
import com.FLsolutions.schoolTrack.models.Kid;
import com.FLsolutions.schoolTrack.models.Parent;

public class KidResponseDto implements ResponseDto {

	private final Long sysId;
	private final String firstName;
	private final String lastName;
	private final DayType dayType;
	private final List<String> parents;

	public KidResponseDto(Kid kid) {
		this.sysId = kid.getSysId();
		this.firstName = kid.getFirstName();
		this.lastName = kid.getLastName();
		this.dayType = kid.getDayType();
		this.parents = getParentsNames(kid.getParents());
	}

	
	public List<String> getParentsNames(List<Parent> parents){
		List<String> parentsNames = new ArrayList<String>();		
		parents.forEach(parent -> {
			String fullName = parent.getFirstName() + " " + parent.getLastName();
			parentsNames.add(fullName);
		});
		return parentsNames;
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

	public DayType getDayType() {
		return dayType;
	}


	public List<String> getParents() {
		return parents;
	}
	
	

}
