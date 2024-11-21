package com.FLsolutions.schoolTrack.dtos;

import lombok.Builder;

@Builder
public class SignInRequestDto {

	private String username;
	private String password;

	public SignInRequestDto() {
	}

	public SignInRequestDto(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
