package com.FLsolutions.schoolTrack.dtos;

public class UserCreationRequestDto implements RequestDto{
	
	public final String firstName;
	public final String lastName;
	public final String telNumber;
	public final String email;
	
	public UserCreationRequestDto() {
		this.firstName = "";
		this.lastName = "";
		this.telNumber = "";
		this.email = "";
	}
	
    public UserCreationRequestDto(String firstName, String lastName, String email, String telNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.telNumber = telNumber;
    }
    
    public UserCreationRequestDto(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
		this.telNumber = "";
		this.email = "";
    }

}
