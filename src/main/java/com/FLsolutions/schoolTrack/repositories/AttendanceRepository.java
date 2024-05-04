package com.FLsolutions.schoolTrack.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.FLsolutions.schoolTrack.models.Attendance;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long>{

}
