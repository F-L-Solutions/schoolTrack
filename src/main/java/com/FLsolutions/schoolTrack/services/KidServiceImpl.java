package com.FLsolutions.schoolTrack.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.FLsolutions.schoolTrack.dtos.KidCreationRequestDto;
import com.FLsolutions.schoolTrack.dtos.KidResponseDto;
import com.FLsolutions.schoolTrack.dtos.StatusResponseDto;
import com.FLsolutions.schoolTrack.exceptions.GenericEventException;
import com.FLsolutions.schoolTrack.exceptions.GenericUserException;
import com.FLsolutions.schoolTrack.models.Kid;
import com.FLsolutions.schoolTrack.models.Parent;
import com.FLsolutions.schoolTrack.repositories.KidRepository;
import com.FLsolutions.schoolTrack.repositories.ParentRepository;

@Service
public class KidServiceImpl implements KidService {

	private KidRepository kidRepository;
	private ParentRepository parentRepository;
//	private Utils utils;

	public KidServiceImpl(KidRepository kidRepository, ParentRepository parentRepository) {
		this.kidRepository = kidRepository;
		this.parentRepository = parentRepository;
	}

	@Override
	public List<KidResponseDto> getAllKids() {
		List<Kid> kids = kidRepository.findAll();
		return kids.stream().map(kid -> new KidResponseDto(kid)).collect(Collectors.toList());
	}

	@Override
	public KidResponseDto fetchKidBySysId(Long sysId) {
		Optional<Kid> optinalKid = kidRepository.findBySysId(sysId);

		if (optinalKid.isPresent()) {
			return new KidResponseDto(optinalKid.get());
		} else
			throw new GenericEventException("A kid with id " + sysId + " doesnt exist.", HttpStatus.NOT_FOUND);
	}

	@Override
	public StatusResponseDto createKid(KidCreationRequestDto request) {
	    StatusResponseDto response = new StatusResponseDto("");

	    // Check if the parent exists
	    Optional<Parent> optionalParent = parentRepository.findBySysId(request.getParentSysId());
	    Parent parent;
	    List<Parent> parentList = new ArrayList<>();

	    if (optionalParent.isPresent()) {
	        parent = optionalParent.get();
	        parentList.add(parent);
	    } else {
	        throw new GenericUserException(
	                "A parent with id " + request.getParentSysId() + " doesn't exist in the database.",
	                HttpStatus.NOT_FOUND);
	    }

	    // Check if a kid with the same first and last name exists
	    Optional<Kid> existingKid = kidRepository.findByFirstNameAndLastName(request.getFirstName(), request.getLastName());

	    if (existingKid.isPresent()) {
	        throw new GenericUserException(
	                "A kid with the name " + request.getFirstName() + " " + request.getLastName() +
	                " already exists.",
	                HttpStatus.CONFLICT);
	    }

	    // Save kid
	    Kid kid = new Kid(request.getFirstName(), request.getLastName(), parentList);
	    kidRepository.save(kid);
	    response.setStatus("Kid was created.");

	    return response;
	}

}
