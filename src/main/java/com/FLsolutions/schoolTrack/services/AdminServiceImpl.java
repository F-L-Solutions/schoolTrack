package com.FLsolutions.schoolTrack.services;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMethod;

import com.FLsolutions.schoolTrack.dtos.AdminCreationRequestDto;
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

		if (adminRepository.findByEmail(request.getEmail()) != null) {
			throw new DuplicateEmailException("Email already exists", HttpStatus.CONFLICT);
		}

		if (adminRepository.findByFirstNameAndLastName(request.getFirstName(), request.getLastName()) != null) {
			throw new GenericUserException("Admin with this name already exists.", HttpStatus.CONFLICT);
		}
		Admin admin = new Admin(request.getFirstName(), request.getLastName(), request.getEmail(),
				request.getAdminRole());
		adminRepository.save(admin);
		responseDto.setStatus("Admin was created");

		return responseDto;
	}

}
