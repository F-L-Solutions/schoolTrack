package com.FLsolutions.schoolTrack.dtos;

import jakarta.validation.constraints.NotBlank;

public class UserCreationRequestDto implements RequestDto {

	@NotBlank(message = "First name is mandatory")
	private String firstName;

	@NotBlank(message = "Last name is mandatory")
	private String lastName;


	public UserCreationRequestDto() {
        this.firstName = "";
        this.lastName = "";
	}

	public UserCreationRequestDto(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}
