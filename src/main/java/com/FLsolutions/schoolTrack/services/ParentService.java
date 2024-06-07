package com.FLsolutions.schoolTrack.services;

import java.util.List;

import com.FLsolutions.schoolTrack.dtos.ParentCreationRequestDto;
import com.FLsolutions.schoolTrack.dtos.ParentResponseDto;
import com.FLsolutions.schoolTrack.dtos.StatusResponseDto;

public interface ParentService {

	List<ParentResponseDto> fetchAllParents();
	
	StatusResponseDto createParent(ParentCreationRequestDto requestDto);
	
	ParentResponseDto fetchParentBySysId(Long sysId);
	
	List<ParentResponseDto> fetchParentsByKidSysId(Long sysId);
}
