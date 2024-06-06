package com.FLsolutions.schoolTrack.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.FLsolutions.schoolTrack.dtos.SubstituteCreditResponseDto;
import com.FLsolutions.schoolTrack.services.SubstituteCreditService;

@RestController
@RequestMapping("/substitutes")
@Validated
public class SubstituteController {
	
	private final SubstituteCreditService creditService;
	
	public SubstituteController (SubstituteCreditService creditService) {
		this.creditService = creditService;
	}
	
	@GetMapping("/credit")
	public ResponseEntity<List<SubstituteCreditResponseDto>> getAllCredit(){
		List<SubstituteCreditResponseDto> responseCreditList = creditService.fetchAllSubstituteCredit();
		return new ResponseEntity<>(responseCreditList, HttpStatus.OK);
	}

}
