package com.FLsolutions.schoolTrack.dtos;

public class KidCreationRequestDto extends UserCreationRequestDto{
	
	private Long parentSysId;

	public KidCreationRequestDto(String firstName, String lastName, Long parentSysId) {
		super(firstName, lastName);
		this.parentSysId = parentSysId;
		
	}
	
	public KidCreationRequestDto() {
		super();
	}

	public Long getParentSysId() {
		return parentSysId;
	}

	public void setParentSysId(Long parentSysId) {
		this.parentSysId = parentSysId;
	}
	
	

}
