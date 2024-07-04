package com.FLsolutions.schoolTrack.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserCreationRequestDto implements RequestDto {

	@Size(max = 20, message = "First name can be at most 20 characters long")
	@Pattern(regexp = "^[a-zA-ZáčďéěíňóřšťúůýžÁČĎÉĚÍŇÓŘŠŤÚŮÝŽ]*$", message = "First name should not contain special characters")
	@NotBlank(message = "First name is mandatory")
	private String firstName;

	@Size(max = 20, message = "Last name can be at most 20 characters long")
	@Pattern(regexp = "^[a-zA-ZáčďéěíňóřšťúůýžÁČĎÉĚÍŇÓŘŠŤÚŮÝŽ]*$", message = "Last name should not contain special characters")
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
