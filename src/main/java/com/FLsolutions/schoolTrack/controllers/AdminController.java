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

import com.FLsolutions.schoolTrack.dtos.AdminCreationRequestDto;
import com.FLsolutions.schoolTrack.dtos.AdminResponseDto;
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
	
	@GetMapping("")
	public ResponseEntity<List<AdminResponseDto>> getAllAdmins(){
		List<AdminResponseDto> response = adminService.fetchAllAdmin();
		return new ResponseEntity<List<AdminResponseDto>>(response, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<AdminResponseDto> getAdminBySysId(@PathVariable("id") Long id){
		AdminResponseDto response = adminService.fetchAdminBySysId(id);
		return new ResponseEntity<AdminResponseDto>(response, HttpStatus.OK);
	}
}
