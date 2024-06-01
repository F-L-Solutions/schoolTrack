package com.FLsolutions.schoolTrack.repositories;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.FLsolutions.schoolTrack.models.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

	@Query("SELECT r FROM Reservation r WHERE r.kid.sysId = :kidId AND r.date = :date AND TYPE(r) = Reservation")
	Optional<Reservation> findByKidIdAndDate(@Param("kidId") Long kidId, @Param("date") LocalDate date);

}
