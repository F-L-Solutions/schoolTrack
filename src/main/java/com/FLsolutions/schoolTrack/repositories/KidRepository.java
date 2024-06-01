package com.FLsolutions.schoolTrack.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.FLsolutions.schoolTrack.dtos.KidResponseDto;
import com.FLsolutions.schoolTrack.models.Kid;

public interface KidRepository extends JpaRepository<Kid, Long> {
	
	List<Kid> findAll();
	
	Optional<Kid> findByUserName(String username);
	
	Optional<Kid> findBySysId(Long sysId);
	
}
