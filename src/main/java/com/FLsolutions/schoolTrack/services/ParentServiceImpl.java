package com.FLsolutions.schoolTrack.services;

import org.springframework.stereotype.Service;

import com.FLsolutions.schoolTrack.dtos.ParentCreationRequestDto;
import com.FLsolutions.schoolTrack.dtos.StatusResponseDto;
import com.FLsolutions.schoolTrack.exceptions.DuplicateEmailException;
import com.FLsolutions.schoolTrack.exceptions.GenericUserException;
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
			throw new DuplicateEmailException("Email already exists");
		}

		if (parentRepository.findByUserName(username) != null) {
			throw new GenericUserException("Username already exists");
		}

		Parent parent = new Parent(requestDto.getFirstName(), requestDto.getLastName(), requestDto.getTelNumber(),
				requestDto.getEmail());
		parentRepository.save(parent);
		responseDto.setStatus("Parent was created");

		return responseDto;
	}
}
