package com.FLsolutions.schoolTrack.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.FLsolutions.schoolTrack.dtos.KidResponseDto;
import com.FLsolutions.schoolTrack.dtos.ParentCreationRequestDto;
import com.FLsolutions.schoolTrack.dtos.ParentResponseDto;
import com.FLsolutions.schoolTrack.dtos.StatusResponseDto;
import com.FLsolutions.schoolTrack.exceptions.DuplicateEmailException;
import com.FLsolutions.schoolTrack.exceptions.GenericUserException;
import com.FLsolutions.schoolTrack.models.Kid;
import com.FLsolutions.schoolTrack.models.Parent;
import com.FLsolutions.schoolTrack.repositories.ParentRepository;

@Service
public class ParentServiceImpl implements ParentService {

	private ParentRepository parentRepository;

	public ParentServiceImpl(ParentRepository parentRepository) {
		this.parentRepository = parentRepository;
	}

	public StatusResponseDto createParent(ParentCreationRequestDto requestDto) {
		StatusResponseDto responseDto = new StatusResponseDto("");
		String username = requestDto.getFirstName() + requestDto.getLastName();

		if (parentRepository.findByEmail(requestDto.getEmail()) != null) {
			throw new DuplicateEmailException("Email already exists", HttpStatus.CONFLICT);
		}

		if (parentRepository.findByUserName(username) != null) {
			throw new GenericUserException("Username already exists", HttpStatus.CONFLICT);
		}

		Parent parent = new Parent(requestDto.getFirstName(), requestDto.getLastName(), requestDto.getTelNumber(),
				requestDto.getEmail());
		parentRepository.save(parent);
		responseDto.setStatus("Parent was created");

		return responseDto;
	}

	@Override
	public List<ParentResponseDto> fetchAllParents() {
		List<Parent> parents = parentRepository.findAll();
		return parents.stream().map(parent -> new ParentResponseDto(parent)).collect(Collectors.toList());
	}
}
