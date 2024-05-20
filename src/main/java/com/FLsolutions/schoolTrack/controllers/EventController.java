package com.FLsolutions.schoolTrack.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.FLsolutions.schoolTrack.dtos.EventCreationRequestDto;
import com.FLsolutions.schoolTrack.dtos.ParentCreationRequestDto;
import com.FLsolutions.schoolTrack.dtos.StatusResponseDto;
import com.FLsolutions.schoolTrack.services.EventService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/events")
public class EventController {

	private final EventService eventService;

	public EventController(EventService eventService) {
		this.eventService = eventService;
	}

	@PostMapping("/bulk-create")
	public ResponseEntity<StatusResponseDto> bulkCreate(@RequestBody EventCreationRequestDto dto) {
		StatusResponseDto response = eventService.bulkCreateEvents(dto);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
