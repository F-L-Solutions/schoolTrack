package com.FLsolutions.schoolTrack.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.FLsolutions.schoolTrack.models.Parent;

@Repository
public interface ParentRepository extends JpaRepository<Parent, Long> {

	Parent findByEmail(String email);

	Parent findByUsername(String username);
	
	List<Parent> findAll();
	
	Optional<Parent> findBySysId(Long sysId);
	
	@Query("SELECT p FROM Parent p JOIN p.kids k WHERE k.sysId = :kidId")
	Optional<List<Parent>> findByKidSysId(@Param("kidId") Long kidId);

}
