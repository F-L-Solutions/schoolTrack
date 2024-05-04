package com.FLsolutions.schoolTrack.services;

import org.springframework.stereotype.Service;

import com.FLsolutions.schoolTrack.repositories.ReservationRepository;

@Service
public class ReservationServiceImpl implements ReservationService {

	private ReservationRepository reservationRepository;

	public ReservationServiceImpl(ReservationRepository reservationRepository) {
		this.reservationRepository = reservationRepository;
	}

}
