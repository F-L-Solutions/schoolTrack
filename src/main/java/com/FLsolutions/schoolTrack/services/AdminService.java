package com.FLsolutions.schoolTrack.services;

import com.FLsolutions.schoolTrack.dtos.AdminCreationRequestDto;
import com.FLsolutions.schoolTrack.dtos.StatusResponseDto;

public interface AdminService {
	
	StatusResponseDto createAdmin(AdminCreationRequestDto request);

}
