package com.FLsolutions.schoolTrack.services;

import org.springframework.stereotype.Service;

import com.FLsolutions.schoolTrack.dtos.ParentCreationRequestDto;
import com.FLsolutions.schoolTrack.dtos.StatusResponseDto;
import com.FLsolutions.schoolTrack.exceptions.MissingRequestBodyException;
import com.FLsolutions.schoolTrack.models.Parent;
import com.FLsolutions.schoolTrack.repositories.ParentRepository;

@Service
public class ParentServiceImpl implements ParentService {

	private ParentRepository parentRepository;

	public ParentServiceImpl(ParentRepository parentRepository) {
		this.parentRepository = parentRepository;
	}

	public StatusResponseDto createParent(ParentCreationRequestDto requestDto) {
		if (requestDto.firstName == null || requestDto.lastName == null || requestDto.email == null
				|| requestDto.telNumber == null) {
			throw new MissingRequestBodyException();
		}

		Parent parent = new Parent(requestDto.firstName, requestDto.lastName, requestDto.email, requestDto.telNumber);
		parentRepository.save(parent);
		return new StatusResponseDto("Parent was created");
	}

}
