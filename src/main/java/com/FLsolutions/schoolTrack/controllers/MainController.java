package com.FLsolutions.schoolTrack.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.FLsolutions.schoolTrack.dtos.KidResponseDto;
import com.FLsolutions.schoolTrack.services.KidService;

@RestController
public class MainController {
	private final KidService kidService;

	public MainController(KidService kidService) {
		this.kidService = kidService;
	}



}
