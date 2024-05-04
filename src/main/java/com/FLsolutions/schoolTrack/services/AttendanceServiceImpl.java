package com.FLsolutions.schoolTrack.services;

import org.springframework.stereotype.Service;
import com.FLsolutions.schoolTrack.repositories.AttendanceRepository;

@Service
public class AttendanceServiceImpl implements AttendanceService {

	private AttendanceRepository attendanceRepository;

	public AttendanceServiceImpl(AttendanceRepository attendanceRepository) {
		this.attendanceRepository = attendanceRepository;
	}

}
