package com.FLsolutions.schoolTrack.services;

import com.FLsolutions.schoolTrack.dtos.ReservationCreationRequestDto;
import com.FLsolutions.schoolTrack.dtos.StatusResponseDto;

public interface ReservationService {
	
	StatusResponseDto createReservation(ReservationCreationRequestDto dto);

}
