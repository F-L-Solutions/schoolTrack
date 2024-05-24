package com.FLsolutions.schoolTrack.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.FLsolutions.schoolTrack.models.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
	
	Optional<Event> findByDate(LocalDate date);
	
	List<Event> findAllByDateBetween(LocalDate startDate, LocalDate endDate);

}
