package com.FLsolutions.schoolTrack.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.parsing.PassThroughSourceExtractor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMethod;

import com.FLsolutions.schoolTrack.dtos.AdminCreationRequestDto;
import com.FLsolutions.schoolTrack.dtos.AdminResponseDto;
import com.FLsolutions.schoolTrack.dtos.StatusResponseDto;
import com.FLsolutions.schoolTrack.exceptions.DuplicateEmailException;
import com.FLsolutions.schoolTrack.exceptions.GenericUserException;
import com.FLsolutions.schoolTrack.models.Admin;
import com.FLsolutions.schoolTrack.repositories.AdminRepository;

@Service
public class AdminServiceImpl implements AdminService {

	private AdminRepository adminRepository;

	public AdminServiceImpl(AdminRepository adminRepository) {
		this.adminRepository = adminRepository;
	}

	@Override
	public StatusResponseDto createAdmin(AdminCreationRequestDto request) {
		StatusResponseDto responseDto = new StatusResponseDto("");

		Optional<Admin> existingAdmin = adminRepository.findByEmail(request.getEmail());
		if (existingAdmin.isPresent()) {
			throw new DuplicateEmailException("Email already exists", HttpStatus.CONFLICT);
		}

		existingAdmin = adminRepository.findByFirstNameAndLastName(request.getFirstName(), request.getLastName());

		if (existingAdmin.isPresent()) {
			throw new GenericUserException("Admin with this name already exists.", HttpStatus.CONFLICT);
		}
		Admin admin = new Admin(request.getFirstName(), request.getLastName(), request.getEmail(),
				request.getAdminRole());
		adminRepository.save(admin);
		responseDto.setStatus("Admin was created");

		return responseDto;
	}

	@Override
	public List<AdminResponseDto> fetchAllAdmin() {
		List<AdminResponseDto> adminDto = new ArrayList<>();

		List<Admin> admins = adminRepository.findAll();

		if (admins.isEmpty()) {
			throw new GenericUserException("No admins exist in the database.", HttpStatus.NOT_FOUND);
		}

		admins.forEach(admin -> adminDto.add(new AdminResponseDto(admin)));

		return adminDto;
	}

	@Override
	public AdminResponseDto fetchAdminBySysId(Long sysId) {
		Optional<Admin> existingAdmin = adminRepository.findBySysId(sysId);

		if (existingAdmin.isEmpty()) {
			throw new GenericUserException("Admin with this sysId doesnt exist in the database: " + sysId,
					HttpStatus.NOT_FOUND);
		}

		AdminResponseDto responseAdmin = new AdminResponseDto(existingAdmin.get());
		return responseAdmin;
	}

}
