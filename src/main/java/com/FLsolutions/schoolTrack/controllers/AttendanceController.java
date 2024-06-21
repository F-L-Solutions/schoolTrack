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

import com.FLsolutions.schoolTrack.dtos.AttendanceCreationRequestDto;
import com.FLsolutions.schoolTrack.dtos.AttendanceResponseDto;
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

	@PostMapping("/{id}/cancel")
	public ResponseEntity<StatusResponseDto> cancel(@PathVariable("id") Long id) {
		StatusResponseDto response = attendanceService.cancelAttendance(id);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("")
	public ResponseEntity<List<AttendanceResponseDto>> getAllAttendances(){
		List<AttendanceResponseDto> response = attendanceService.fetchAllAttendances();
		return new ResponseEntity<List<AttendanceResponseDto>>(response, HttpStatus.OK);
	}
	
	@GetMapping("/kid/{id}")
	public ResponseEntity<List<AttendanceResponseDto>> getAttendancesByKidSysId(@PathVariable("id") Long id){
		List<AttendanceResponseDto> response = attendanceService.fetchAttendanceByKidSysId(id);
		return new ResponseEntity<List<AttendanceResponseDto>>(response, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<AttendanceResponseDto> getAttendanceBySysId(@PathVariable("id") Long id){
		AttendanceResponseDto response= attendanceService.fetchAttendanceBySysId(id);
		return new ResponseEntity<AttendanceResponseDto>(response, HttpStatus.OK);
	}

}
