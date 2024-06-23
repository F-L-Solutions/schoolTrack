package com.FLsolutions.schoolTrack.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.FLsolutions.schoolTrack.dtos.AttendanceResponseDto;
import com.FLsolutions.schoolTrack.dtos.ReservationCreationRequestDto;
import com.FLsolutions.schoolTrack.dtos.ReservationResponseDto;
import com.FLsolutions.schoolTrack.dtos.StatusResponseDto;
import com.FLsolutions.schoolTrack.exceptions.DuplicateReservationException;
import com.FLsolutions.schoolTrack.exceptions.GenericEventException;
import com.FLsolutions.schoolTrack.exceptions.GenericReservationException;
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

	@Override
	public StatusResponseDto createReservation(ReservationCreationRequestDto request) {
		StatusResponseDto response = new StatusResponseDto("");

		Kid kid = kidRepository.findByUserName(request.getKidUserName())
				.orElseThrow(() -> new KidNotFoundException("Selected kid username was not found in the database",
						HttpStatus.NOT_FOUND));

		Optional<Reservation> existingReservation = reservationRepository.findByKidIdAndDate(kid.getSysId(),
				request.getDate());

		if (existingReservation.isPresent()) {
			throw new DuplicateReservationException("Selected kid already has a reservation for this day",
					HttpStatus.CONFLICT);
		}

		Event existingEvent = eventRepository.findByDate(request.getDate())
				.orElseThrow(() -> new GenericEventException("For selected date there was no event found in database",
						HttpStatus.NOT_FOUND));

		if (existingEvent.getAvailableSpots() != 0) {
			throw new GenericEventException("There are available spots for the day, reservation was not created",
					HttpStatus.CONFLICT);
		}

		Reservation reservation = new Reservation(request.getDate(), request.getDayType(), kid);
		reservationRepository.save(reservation);
		response.setStatus("Reservation for " + request.getKidUserName() + " was created.");

		return response;
	}

	@Override
	public ReservationResponseDto fetchReservationBySysId(Long id) {

		Reservation reservation = reservationRepository.findById(id).orElseThrow(() -> new GenericReservationException(
				"Reservation with this id was not found in the database.", HttpStatus.NOT_FOUND));

		ReservationResponseDto reservationResponse = new ReservationResponseDto(reservation);
		return reservationResponse;
	}

	@Override
	public List<ReservationResponseDto> fetchAllReservations() {
		List<ReservationResponseDto> reservationsResponse = new ArrayList<ReservationResponseDto>();
		List<Reservation> reservations = reservationRepository.findAll();

		if (reservations.isEmpty()) {
			throw new GenericReservationException("There are no reservations in the database", HttpStatus.NOT_FOUND);
		}

		reservations.forEach(reservation -> reservationsResponse.add(new ReservationResponseDto(reservation)));
		return reservationsResponse;
	}
}
