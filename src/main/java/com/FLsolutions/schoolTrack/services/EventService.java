package com.FLsolutions.schoolTrack.services;

import java.util.List;

import com.FLsolutions.schoolTrack.dtos.EventCreationRequestDto;
import com.FLsolutions.schoolTrack.dtos.EventResponseDto;
import com.FLsolutions.schoolTrack.dtos.StatusResponseDto;

public interface EventService {
	
	StatusResponseDto bulkCreateEvents(EventCreationRequestDto dto);
	
	List<EventResponseDto> fetchAllEvents();
	
	EventResponseDto fetchBySysId(Long sysId);
	
}
