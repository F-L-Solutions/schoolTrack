package com.FLsolutions.schoolTrack.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.FLsolutions.schoolTrack.dtos.EventCreationRequestDto;
import com.FLsolutions.schoolTrack.dtos.EventResponseDto;
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

	@GetMapping("")
	public ResponseEntity<List<EventResponseDto>> getAllEvents() {
		List<EventResponseDto> response = eventService.fetchAllEvents();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<EventResponseDto> getEventBySysId(@PathVariable("id") Long id) {
		EventResponseDto response = eventService.fetchBySysId(id);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
