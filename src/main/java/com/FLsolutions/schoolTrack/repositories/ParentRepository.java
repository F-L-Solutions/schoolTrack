package com.FLsolutions.schoolTrack.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.FLsolutions.schoolTrack.models.Parent;

@Repository
public interface ParentRepository extends JpaRepository<Parent, Long> {

	Parent findByEmail(String email);

	Parent findByUserName(String userName);
	
	List<Parent> findAll();

}
