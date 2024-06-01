package com.FLsolutions.schoolTrack.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.FLsolutions.schoolTrack.dtos.KidResponseDto;
import com.FLsolutions.schoolTrack.exceptions.GenericEventException;
import com.FLsolutions.schoolTrack.models.Kid;
import com.FLsolutions.schoolTrack.repositories.KidRepository;

@Service
public class KidServiceImpl implements KidService {
	
	private KidRepository kidRepository;

	public KidServiceImpl(KidRepository kidRepository) {
		this.kidRepository = kidRepository;
	}

	@Override
	public List<KidResponseDto> getAllKids() {
		List<Kid> kids = kidRepository.findAll();
		return kids.stream().map(kid -> new KidResponseDto(kid)).collect(Collectors.toList());
	}

	@Override
	public KidResponseDto fetchKidBySysId(Long sysId) {
		Optional<Kid> optinalKid = kidRepository.findBySysId(sysId);

		if(optinalKid.isPresent()) {
			return new KidResponseDto(optinalKid.get());
		} else throw new GenericEventException("A kid with id " + sysId + " doesnt exist.",
				HttpStatus.NOT_FOUND);
	}
	
	


}
