package com.FLsolutions.schoolTrack.repositories;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.FLsolutions.schoolTrack.models.Attendance;
import com.FLsolutions.schoolTrack.models.AttendanceDay;

import java.util.List;


@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

	@Query("SELECT a FROM Attendance a WHERE a.kid.sysId = :kidId AND a.date = :date AND TYPE(a) = Attendance")
	Optional<Attendance> findByKidIdAndDate(@Param("kidId") Long kidId, @Param("date") LocalDate date);
	
	Optional<List<Attendance>> findByAttendanceDay(AttendanceDay attendanceDay);
}
