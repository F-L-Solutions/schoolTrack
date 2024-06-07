package com.FLsolutions.schoolTrack.services;

import java.util.List;

import com.FLsolutions.schoolTrack.dtos.AttendanceCreationRequestDto;
import com.FLsolutions.schoolTrack.dtos.AttendanceResponseDto;
import com.FLsolutions.schoolTrack.dtos.StatusResponseDto;

public interface AttendanceService {
	
	StatusResponseDto bulkCreateAttendances(AttendanceCreationRequestDto dto);
	
	StatusResponseDto createAttendance(AttendanceCreationRequestDto dto);
	
	StatusResponseDto cancelAttendance(Long attendanceId);
	
	List<AttendanceResponseDto> fetchAllAttendances();
	
	List<AttendanceResponseDto> fetchAttendanceByKidSysId(Long sysId);

}
