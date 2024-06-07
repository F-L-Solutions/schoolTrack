package com.FLsolutions.schoolTrack.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.FLsolutions.schoolTrack.dtos.ParentCreationRequestDto;
import com.FLsolutions.schoolTrack.dtos.ParentResponseDto;
import com.FLsolutions.schoolTrack.dtos.StatusResponseDto;
import com.FLsolutions.schoolTrack.services.ParentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/parents")
@Validated
public class ParentController {

	private final ParentService parentService;

	public ParentController(ParentService parentService) {
		this.parentService = parentService;
	}

	@PostMapping("")
	public ResponseEntity<StatusResponseDto> create(@Valid @RequestBody ParentCreationRequestDto dto) {
		StatusResponseDto responseDto = parentService.createParent(dto);
		return new ResponseEntity<>(responseDto, HttpStatus.OK);
	}

	@GetMapping("")
	public ResponseEntity<List<ParentResponseDto>> getAllParents() {
		List<ParentResponseDto> responseParentList = parentService.fetchAllParents();
		return new ResponseEntity<>(responseParentList, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ParentResponseDto> getParentBySysId(@PathVariable("id") Long id) {
		ParentResponseDto responseParent = parentService.fetchParentBySysId(id);
		return new ResponseEntity<>(responseParent, HttpStatus.OK);
	}

	@GetMapping("/kid/{id}")
	public ResponseEntity<List<ParentResponseDto>> getParentsByKidSysId(@PathVariable("id") Long kidId) {
		List<ParentResponseDto> responseParentList = parentService.fetchParentsByKidSysId(kidId);
		return new ResponseEntity<>(responseParentList, HttpStatus.OK);
	}

}
