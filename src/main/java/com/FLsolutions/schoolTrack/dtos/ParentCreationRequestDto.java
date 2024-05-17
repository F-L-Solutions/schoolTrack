package com.FLsolutions.schoolTrack.dtos;

import java.util.List;

public class ParentCreationRequestDto extends UserCreationRequestDto {

	private List<KidCreationRequestDto> kids;

	public ParentCreationRequestDto(String firstName, String lastName, String email, String telNumber) {
		super(firstName, lastName, email, telNumber);
		// this.kids= kids;

	}

}
