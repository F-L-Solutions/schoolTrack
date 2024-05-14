package com.FLsolutions.schoolTrack.services;

import org.springframework.stereotype.Service;

import com.FLsolutions.schoolTrack.repositories.SubstituteCreditRepository;

@Service
public class SubstituteCreditServiceImpl implements SubstituteCreditService {

	private SubstituteCreditRepository substituteCreditRepository;

	public SubstituteCreditServiceImpl(SubstituteCreditRepository substituteCreditRepository) {
		this.substituteCreditRepository = substituteCreditRepository;
	}

}
