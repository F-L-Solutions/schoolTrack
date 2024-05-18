package com.FLsolutions.schoolTrack.dtos;

import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class ParentCreationRequestDto extends UserCreationRequestDto {

	private List<KidCreationRequestDto> kids;

	// @NotBlank(message = "Telephone number is mandatory")
	private String telNumber;

	@Email(message = "Email should be valid")
	@NotBlank(message = "Email is mandatory")
	private String email;

	public ParentCreationRequestDto() {
		super();
	}

	public ParentCreationRequestDto(String firstName, String lastName, String telNumber, String email) {
		super(firstName, lastName);
		this.telNumber = telNumber;
		this.email = email;
		// this.kids= kids;
	}

	public List<KidCreationRequestDto> getKids() {
		return kids;
	}

	public void setKids(List<KidCreationRequestDto> kids) {
		this.kids = kids;
	}

	public String getTelNumber() {
		return telNumber;
	}

	public void setTelNumber(String telNumber) {
		this.telNumber = telNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
