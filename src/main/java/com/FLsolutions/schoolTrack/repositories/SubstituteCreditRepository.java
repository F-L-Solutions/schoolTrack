package com.FLsolutions.schoolTrack.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.FLsolutions.schoolTrack.models.SubstituteCredit;

@Repository
public interface SubstituteCreditRepository extends JpaRepository<SubstituteCredit, Long> {
	
	List<SubstituteCredit> findAll();

}
