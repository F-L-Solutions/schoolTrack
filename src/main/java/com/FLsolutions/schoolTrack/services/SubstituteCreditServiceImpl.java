package com.FLsolutions.schoolTrack.services;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

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
		LocalDateTime expirationDate = LocalDateTime.now().plusWeeks(1);
		SubstituteCredit substituteCredit = new SubstituteCredit(kid, expirationDate);
		substituteCreditRepository.save(substituteCredit);
	}

}
