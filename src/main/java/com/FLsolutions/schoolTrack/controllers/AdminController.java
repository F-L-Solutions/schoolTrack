package com.FLsolutions.schoolTrack.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.FLsolutions.schoolTrack.dtos.AdminCreationRequestDto;
import com.FLsolutions.schoolTrack.dtos.StatusResponseDto;
import com.FLsolutions.schoolTrack.services.AdminService;

@RestController
@RequestMapping("/admins")
@Validated
public class AdminController {

	private final AdminService adminService;

	public AdminController(AdminService adminService) {
		this.adminService = adminService;
	}

	@PostMapping("")
	public ResponseEntity<StatusResponseDto> create(@RequestBody AdminCreationRequestDto dto) {
		StatusResponseDto response = adminService.createAdmin(dto);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
