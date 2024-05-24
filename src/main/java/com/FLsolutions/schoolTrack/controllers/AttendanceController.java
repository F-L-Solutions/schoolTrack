package com.FLsolutions.schoolTrack.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.FLsolutions.schoolTrack.dtos.AttendanceCreationRequestDto;
import com.FLsolutions.schoolTrack.dtos.StatusResponseDto;
import com.FLsolutions.schoolTrack.services.AttendanceService;

@Controller
@RequestMapping("/attendances")
public class AttendanceController {

	private final AttendanceService attendanceService;

	public AttendanceController(AttendanceService attendanceService) {
		this.attendanceService = attendanceService;
	}

	@PostMapping("")
	public ResponseEntity<StatusResponseDto> create(@RequestBody AttendanceCreationRequestDto dto) {
		StatusResponseDto response = attendanceService.createAttendance(dto);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping("/bulk-create")
	public ResponseEntity<StatusResponseDto> bulkCreate(@RequestBody AttendanceCreationRequestDto dto) {
		StatusResponseDto response = attendanceService.bulkCreateAttendances(dto);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
