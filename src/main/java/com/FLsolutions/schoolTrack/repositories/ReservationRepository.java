package com.FLsolutions.schoolTrack.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.FLsolutions.schoolTrack.models.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long>{

}
