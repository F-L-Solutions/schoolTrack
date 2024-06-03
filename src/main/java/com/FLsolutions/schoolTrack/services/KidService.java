package com.FLsolutions.schoolTrack.services;

import java.util.List;

import com.FLsolutions.schoolTrack.dtos.KidCreationRequestDto;
import com.FLsolutions.schoolTrack.dtos.KidResponseDto;
import com.FLsolutions.schoolTrack.dtos.StatusResponseDto;

public interface KidService {

	List<KidResponseDto> getAllKids();
	
	KidResponseDto fetchKidBySysId(Long sysId);
	
	StatusResponseDto createKid(KidCreationRequestDto request, Long parentSysId);

}
