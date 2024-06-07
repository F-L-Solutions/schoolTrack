package com.FLsolutions.schoolTrack.services;


import java.util.List;

import com.FLsolutions.schoolTrack.dtos.SubstituteCreditResponseDto;
import com.FLsolutions.schoolTrack.models.Kid;

public interface SubstituteCreditService {

	void createSubstituteCredit(Kid kid);
	
	List<SubstituteCreditResponseDto> fetchAllSubstituteCredit();
	
	SubstituteCreditResponseDto fetchSubstituteCreditBySysId(long sysId);

}
