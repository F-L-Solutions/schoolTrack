package com.FLsolutions.schoolTrack.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.FLsolutions.schoolTrack.dtos.AdminCreationRequestDto;
import com.FLsolutions.schoolTrack.dtos.AdminResponseDto;
import com.FLsolutions.schoolTrack.dtos.StatusResponseDto;
import com.FLsolutions.schoolTrack.exceptions.GenericUserException;
import com.FLsolutions.schoolTrack.models.Admin;
import com.FLsolutions.schoolTrack.repositories.AdminRepository;
import com.FLsolutions.schoolTrack.repositories.UserRepository;

@Service
public class AdminServiceImpl extends UserServiceImpl implements AdminService {

	private AdminRepository adminRepository;

	public AdminServiceImpl(AdminRepository adminRepository, UserRepository userRepository,
			PasswordEncoder passwordEncoder) {
		super(userRepository, passwordEncoder);
		this.adminRepository = adminRepository;
	}

	@Override
	public StatusResponseDto createAdmin(AdminCreationRequestDto request) {
		StatusResponseDto responseDto = new StatusResponseDto("");

		validateUniqueEmail(request.getEmail());
		validateUniqueUsername(request.getFirstName() + request.getLastName());

		Admin admin = new Admin(request.getFirstName(), request.getLastName(), request.getEmail(), request.getRole());

		admin.setRole(request.getRole());
		save(admin);

		responseDto.setStatus(
				"Admin " + request.getFirstName() + " " + request.getLastName() + " was created");

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
