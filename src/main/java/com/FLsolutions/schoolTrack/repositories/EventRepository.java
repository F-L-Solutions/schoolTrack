package com.FLsolutions.schoolTrack.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.FLsolutions.schoolTrack.models.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

}
