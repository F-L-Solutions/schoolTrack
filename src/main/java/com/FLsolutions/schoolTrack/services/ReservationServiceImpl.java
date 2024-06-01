package com.FLsolutions.schoolTrack.services;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.FLsolutions.schoolTrack.dtos.ReservationCreationRequestDto;
import com.FLsolutions.schoolTrack.dtos.StatusResponseDto;
import com.FLsolutions.schoolTrack.exceptions.DuplicateReservationException;
import com.FLsolutions.schoolTrack.exceptions.GenericEventException;
import com.FLsolutions.schoolTrack.exceptions.KidNotFoundException;
import com.FLsolutions.schoolTrack.models.Event;
import com.FLsolutions.schoolTrack.models.Kid;
import com.FLsolutions.schoolTrack.models.Reservation;
import com.FLsolutions.schoolTrack.repositories.EventRepository;
import com.FLsolutions.schoolTrack.repositories.KidRepository;
import com.FLsolutions.schoolTrack.repositories.ReservationRepository;

@Service
public class ReservationServiceImpl implements ReservationService {

	private ReservationRepository reservationRepository;
	private KidRepository kidRepository;
	private EventRepository eventRepository;

	public ReservationServiceImpl(ReservationRepository reservationRepository, KidRepository kidRepository,
			EventRepository eventRepository) {
		this.reservationRepository = reservationRepository;
		this.kidRepository = kidRepository;
		this.eventRepository = eventRepository;
	}

	public StatusResponseDto createReservation(ReservationCreationRequestDto request) {
		StatusResponseDto response = new StatusResponseDto("");
		System.out.println("36");
		
		Kid kid = kidRepository.findByUserName(request.getKidUserName())
				.orElseThrow(() -> new KidNotFoundException("Selected kid username was not found in the database",
						HttpStatus.NOT_FOUND));
		System.out.println("41");
		Optional<Reservation> existingReservation = reservationRepository.findByKidIdAndDate(kid.getSysId(),
				request.getDate());
		System.out.println("44");
		if (existingReservation.isPresent()) {
			throw new DuplicateReservationException("Selected kid already has a reservation for this day",
					HttpStatus.CONFLICT);
		}
		System.out.println("49");
		Event existingEvent = eventRepository.findByDate(request.getDate())
				.orElseThrow(() -> new GenericEventException("For selected date there was no event found in database",
						HttpStatus.NOT_FOUND));
		System.out.println("53");
		if (existingEvent.getAvailableSpots() != 0) {
			throw new GenericEventException("There are available spots for the day, reservation was not created",
					HttpStatus.CONFLICT);
		}
		System.out.println("58");
		Reservation reservation = new Reservation(request.getDate(), request.getDayType(), kid);
		reservationRepository.save(reservation);
		response.setStatus("Reservation for " + request.getKidUserName() + " was created.");

		return response;
	}
}
