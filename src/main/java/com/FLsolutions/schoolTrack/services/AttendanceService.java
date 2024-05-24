package com.FLsolutions.schoolTrack.services;

import com.FLsolutions.schoolTrack.dtos.AttendanceCreationRequestDto;
import com.FLsolutions.schoolTrack.dtos.StatusResponseDto;

public interface AttendanceService {
	
	StatusResponseDto bulkCreateAttendances(AttendanceCreationRequestDto dto);
	
	StatusResponseDto createAttendance(AttendanceCreationRequestDto dto);

}
