package com.FLsolutions.schoolTrack.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.FLsolutions.schoolTrack.models.Kid;

public interface KidRepository extends JpaRepository<Kid, Long> {
	
	List<Kid> findAll();
	
	Kid getByUserName(String userName);
	
}
