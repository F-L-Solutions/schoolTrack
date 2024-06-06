package com.FLsolutions.schoolTrack.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.FLsolutions.schoolTrack.dtos.SubstituteCreditResponseDto;
import com.FLsolutions.schoolTrack.exceptions.GenericEventException;
import com.FLsolutions.schoolTrack.models.Kid;
import com.FLsolutions.schoolTrack.models.SubstituteCredit;
import com.FLsolutions.schoolTrack.repositories.SubstituteCreditRepository;

@Service
public class SubstituteCreditServiceImpl implements SubstituteCreditService {

	private SubstituteCreditRepository substituteCreditRepository;

	public SubstituteCreditServiceImpl(SubstituteCreditRepository substituteCreditRepository) {
		this.substituteCreditRepository = substituteCreditRepository;
	}

	public void createSubstituteCredit(Kid kid) {		
		SubstituteCredit substituteCredit = new SubstituteCredit(kid);
		substituteCreditRepository.save(substituteCredit);
	}

	@Override
	public List<SubstituteCreditResponseDto> fetchAllSubstituteCredit() {
	    List<SubstituteCredit> existingCredits = substituteCreditRepository.findAll();
	    
	    if (existingCredits.isEmpty()) {
	        throw new GenericEventException("There are no credits in the database.", HttpStatus.NOT_FOUND);
	    }
	    
	    List<SubstituteCreditResponseDto> creditList = new ArrayList<>();
	    existingCredits.forEach(credit -> creditList.add(new SubstituteCreditResponseDto(credit)));
	    
	    return creditList;
	}

}
