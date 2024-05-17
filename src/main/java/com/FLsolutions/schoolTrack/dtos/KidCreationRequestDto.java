package com.FLsolutions.schoolTrack.dtos;

public class KidCreationRequestDto extends UserCreationRequestDto{

	public KidCreationRequestDto(String firstName, String lastName) {
		super(firstName, lastName);
	}
	
	public KidCreationRequestDto() {
		super();
	}

}
