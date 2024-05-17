package com.FLsolutions.schoolTrack.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.FLsolutions.schoolTrack.dtos.ParentCreationRequestDto;
import com.FLsolutions.schoolTrack.dtos.StatusResponseDto;
import com.FLsolutions.schoolTrack.services.ParentService;

@RestController
@RequestMapping("/parents")
public class ParentController {
	
	private final ParentService parentService;
	
	public ParentController(ParentService parentService) {
		this.parentService= parentService;
	}
	
	@PostMapping("")
	public ResponseEntity<StatusResponseDto> create(@RequestBody ParentCreationRequestDto dto){
		StatusResponseDto responseDto = parentService.createParent(dto);
		return new ResponseEntity<>(responseDto, HttpStatus.OK);
	}

}
