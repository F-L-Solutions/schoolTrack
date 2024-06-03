package com.FLsolutions.schoolTrack.dtos;

import java.util.ArrayList;
import java.util.List;

import com.FLsolutions.schoolTrack.models.Kid;
import com.FLsolutions.schoolTrack.models.Parent;

public class ParentResponseDto implements ResponseDto {
	
	private final Long sysId;
	private final String firstName;
	private final String lastName;
	private final List<String> kids;
	
	public ParentResponseDto(Parent parent) {
		this.sysId = parent.getSysId();
		this.firstName = parent.getFirstName();
		this.lastName = parent.getLastName();
		this.kids = getKidsNames(parent.getKids());
	}
	
	public List<String> getKidsNames(List<Kid> kids){
		List<String> kidsNames = new ArrayList<String>();		
		kids.forEach(kid -> {
			String fullName = kid.getFirstName() + " " + kid.getLastName();
			kidsNames.add(fullName);
		});
		return kidsNames;
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

	public List<String> getKids() {
		return kids;
	}
	
	

}
