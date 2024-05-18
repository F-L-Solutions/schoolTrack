package com.FLsolutions.schoolTrack.dtos;

public class StatusResponseDto implements ResponseDto {
	private String status;

	public StatusResponseDto(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
