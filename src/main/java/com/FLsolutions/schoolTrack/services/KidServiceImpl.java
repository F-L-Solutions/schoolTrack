package com.FLsolutions.schoolTrack.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.FLsolutions.schoolTrack.dtos.KidResponseDto;
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


}
