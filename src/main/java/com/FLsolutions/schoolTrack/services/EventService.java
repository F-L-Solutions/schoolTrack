package com.FLsolutions.schoolTrack.services;

import com.FLsolutions.schoolTrack.dtos.EventCreationRequestDto;
import com.FLsolutions.schoolTrack.dtos.StatusResponseDto;

public interface EventService {
	
	StatusResponseDto bulkCreateEvents(EventCreationRequestDto dto);

}
