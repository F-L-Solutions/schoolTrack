package com.FLsolutions.schoolTrack.services;

import com.FLsolutions.schoolTrack.dtos.ParentCreationRequestDto;
import com.FLsolutions.schoolTrack.dtos.StatusResponseDto;

public interface ParentService {

	StatusResponseDto createParent(ParentCreationRequestDto requestDto);
}
