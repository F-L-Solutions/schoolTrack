package com.FLsolutions.schoolTrack.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.FLsolutions.schoolTrack.dtos.KidResponseDto;
import com.FLsolutions.schoolTrack.models.AttendanceDay;
import com.FLsolutions.schoolTrack.models.Kid;

public interface KidRepository extends JpaRepository<Kid, Long> {

	List<Kid> findAll();

	Optional<Kid> findByUserName(String username);

	Optional<Kid> findBySysId(Long sysId);
	
	
//  Leaving the query here for possible future use
//	@Query(value = "SELECT k.* FROM `school-track`.kids k "
//			+ "JOIN `school-track`.parent_kid pk ON k.sys_id = pk.kid_id "
//			+ "JOIN `school-track`.parents p ON pk.parent_id = p.sys_id "
//			+ "WHERE k.first_name = :firstName AND k.last_name = :lastName AND p.sys_id = :parentId", nativeQuery = true)
//	Optional<Kid> findByFirstNameAndLastNameAndParentId(@Param("parentId") Long parentId,
//			@Param("firstName") String firstName, @Param("lastName") String lastName);

}
