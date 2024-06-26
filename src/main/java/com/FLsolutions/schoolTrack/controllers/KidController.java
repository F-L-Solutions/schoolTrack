package com.FLsolutions.schoolTrack.controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.FLsolutions.schoolTrack.dtos.AttendanceCreationRequestDto;
import com.FLsolutions.schoolTrack.dtos.KidCreationRequestDto;
import com.FLsolutions.schoolTrack.dtos.KidResponseDto;
import com.FLsolutions.schoolTrack.dtos.StatusResponseDto;
import com.FLsolutions.schoolTrack.models.AttendanceDay;
import com.FLsolutions.schoolTrack.services.KidService;

@RestController
@RequestMapping("/kids")
@Validated
public class KidController {
	
	private final KidService kidService;
	
	public KidController(KidService kidService) {
		this.kidService = kidService;
	}
	
	@GetMapping("")
	public ResponseEntity<List<KidResponseDto>> getAllKids() {
		List<KidResponseDto> responseKidList = kidService.getAllKids();
		return new ResponseEntity<>(responseKidList, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<KidResponseDto> getKidBySysId(@PathVariable("id") Long id){
		KidResponseDto kidResponseDto = kidService.fetchKidBySysId(id);
	    
		if (kidResponseDto != null) {
            return new ResponseEntity<>(kidResponseDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
	}
	
	@PostMapping("")
	public ResponseEntity<StatusResponseDto> create(@RequestBody KidCreationRequestDto dto) {
		StatusResponseDto response = kidService.createKid(dto);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/attendance/{day}")
	public ResponseEntity<List<String>> getKidsByAttendanceDay(@PathVariable("day") String day) {
		String attendanceDay = day.toUpperCase();
		List<String> responseKidList = kidService.fetchKidsByAttendanceDay(AttendanceDay.valueOf(attendanceDay));
		return new ResponseEntity<>(responseKidList, HttpStatus.OK);
	}
	
	@GetMapping("/parent/{id}")
	public ResponseEntity<List<KidResponseDto>> getKidsByParentId(@PathVariable("id") Long parentId){
		List<KidResponseDto> responseKidList = kidService.fetchKidsByParentId(parentId);
		return new ResponseEntity<>(responseKidList, HttpStatus.OK);
	}
	
	@GetMapping("/attendance-date/{date}")
	public ResponseEntity<List<KidResponseDto>> getKidsByAttendanceDate(@PathVariable("date") LocalDate date){
		List<KidResponseDto> responseKidList = kidService.fetchKidsByAttendanceDate(date);
		return new ResponseEntity<>(responseKidList, HttpStatus.OK);
	}

}
