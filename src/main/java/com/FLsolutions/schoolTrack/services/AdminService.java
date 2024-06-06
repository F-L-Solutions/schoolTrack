package com.FLsolutions.schoolTrack.services;

import java.util.List;

import com.FLsolutions.schoolTrack.dtos.AdminCreationRequestDto;
import com.FLsolutions.schoolTrack.dtos.AdminResponseDto;
import com.FLsolutions.schoolTrack.dtos.StatusResponseDto;

public interface AdminService {
	
	StatusResponseDto createAdmin(AdminCreationRequestDto request);
	
	List<AdminResponseDto> fetchAllAdmin();
	
	AdminResponseDto fetchAdminBySysId(Long sysId);
	
}
