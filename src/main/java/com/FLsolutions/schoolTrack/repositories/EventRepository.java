package com.FLsolutions.schoolTrack.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.FLsolutions.schoolTrack.models.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

	@Query("SELECT e FROM Event e WHERE e.date = :date AND TYPE(e) = Event") 
	Optional<Event> findByDate(@Param("date") LocalDate date);
	
	@Query("SELECT e FROM Event e WHERE e.sysId = :sysId AND TYPE(e) = Event")
	Optional<Event> findEventById(@Param("sysId") Long sysId);

	@Query("SELECT e FROM Event e WHERE e.date BETWEEN :startDate AND :endDate AND e.class = Event")
	List<Event> findAllByDateBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
	
	@Query("SELECT e FROM Event e WHERE e.class = Event")
	List<Event> findAll();

}
