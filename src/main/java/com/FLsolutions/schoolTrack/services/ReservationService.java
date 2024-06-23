package com.FLsolutions.schoolTrack.services;

import java.util.List;

import com.FLsolutions.schoolTrack.dtos.ReservationCreationRequestDto;
import com.FLsolutions.schoolTrack.dtos.ReservationResponseDto;
import com.FLsolutions.schoolTrack.dtos.StatusResponseDto;

public interface ReservationService {

	StatusResponseDto createReservation(ReservationCreationRequestDto dto);

	ReservationResponseDto fetchReservationBySysId(Long id);

	List<ReservationResponseDto> fetchAllReservations();

}
