package com.FLsolutions.schoolTrack.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.FLsolutions.schoolTrack.models.Admin;
import java.util.List;


public interface AdminRepository extends JpaRepository<Admin, Long>{
	
	Optional<Admin> findByEmail(String email);
	
	Admin findByUsername(String username);
	
	Optional<Admin> findByFirstNameAndLastName(String firstName, String lastName);
	
	List<Admin> findAll();
	
	Optional<Admin> findBySysId(Long sysId);
	

}
