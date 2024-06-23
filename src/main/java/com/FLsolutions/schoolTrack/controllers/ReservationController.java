package com.FLsolutions.schoolTrack.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.FLsolutions.schoolTrack.dtos.ReservationCreationRequestDto;
import com.FLsolutions.schoolTrack.dtos.ReservationResponseDto;
import com.FLsolutions.schoolTrack.dtos.StatusResponseDto;
import com.FLsolutions.schoolTrack.services.ReservationService;

@Controller
@RequestMapping("/reservations")
public class ReservationController {

	private final ReservationService reservationService;

	public ReservationController(ReservationService reservationService) {
		this.reservationService = reservationService;
	}

	@PostMapping("")
	public ResponseEntity<StatusResponseDto> create(@RequestBody ReservationCreationRequestDto dto) {
		StatusResponseDto response = reservationService.createReservation(dto);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ReservationResponseDto> getReservationBySysId(@PathVariable("id") Long id) {
		ReservationResponseDto response = reservationService.fetchReservationBySysId(id);
		return new ResponseEntity<ReservationResponseDto>(response, HttpStatus.OK);
	}

	@GetMapping("")
	public ResponseEntity<List<ReservationResponseDto>> getAllReservations() {
		List<ReservationResponseDto> response = reservationService.fetchAllReservations();
		return new ResponseEntity<List<ReservationResponseDto>>(response, HttpStatus.OK);
	}

	@GetMapping("/kid/{id}")
	public ResponseEntity<List<ReservationResponseDto>> getReservationsByKidId(@PathVariable("id") Long id) {
		List<ReservationResponseDto> response = reservationService.fetchReservationsByKidSysId(id);
		return new ResponseEntity<List<ReservationResponseDto>>(response, HttpStatus.OK);
	}

}
