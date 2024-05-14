package com.FLsolutions.schoolTrack.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.FLsolutions.schoolTrack.models.SubstituteCredit;

@Repository
public interface SubstituteCreditRepository extends JpaRepository<SubstituteCredit, Long> {

}
