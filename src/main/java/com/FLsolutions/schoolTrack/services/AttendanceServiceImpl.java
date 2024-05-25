package com.FLsolutions.schoolTrack.services;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.FLsolutions.schoolTrack.dtos.AttendanceCreationRequestDto;
import com.FLsolutions.schoolTrack.dtos.StatusResponseDto;
import com.FLsolutions.schoolTrack.exceptions.KidNotFoundException;
import com.FLsolutions.schoolTrack.models.Attendance;
import com.FLsolutions.schoolTrack.models.AttendanceStatus;
import com.FLsolutions.schoolTrack.models.DayType;
import com.FLsolutions.schoolTrack.models.Kid;
import com.FLsolutions.schoolTrack.repositories.AttendanceRepository;
import com.FLsolutions.schoolTrack.repositories.KidRepository;

@Service
public class AttendanceServiceImpl implements AttendanceService {

	private AttendanceRepository attendanceRepository;
	private KidRepository kidRepository;

	public AttendanceServiceImpl(AttendanceRepository attendanceRepository, KidRepository kidRepository) {
		this.attendanceRepository = attendanceRepository;
		this.kidRepository = kidRepository;
	}

	public StatusResponseDto createAttendance(AttendanceCreationRequestDto request) {
		StatusResponseDto response = new StatusResponseDto("");

		Kid kid = kidRepository.findByUserName(request.getKidUserName())
				.orElseThrow(() -> new KidNotFoundException("Selected kid username was not found in the database",
						HttpStatus.NOT_FOUND));
		Attendance attendance = new Attendance(request.getDate(), request.getDayType(), kid);
		attendanceRepository.save(attendance);
		response.setStatus("Attendance for " + request.getKidUserName() + " was created.");

		return response;
	}

	public StatusResponseDto bulkCreateAttendances(AttendanceCreationRequestDto dto) {
		StatusResponseDto response = new StatusResponseDto("");
		return response;
	}

	/*
	 * private List<LocalDate> checkKidsExistingAttendance(LocalDate date, Kid kid)
	 * { LocalDate currentDate = generationStartDate; List<Event> existingEvents =
	 * eventRepository.findAllByDateBetween(generationStartDate, generationEndDate);
	 * Set<LocalDate> existingDates =
	 * existingEvents.stream().map(Event::getDate).collect(Collectors.toSet());
	 * List<LocalDate> conflictingDates = new ArrayList<>();
	 * 
	 * if (generationStartDate.isAfter(generationEndDate)) { throw new
	 * GenericEventException("0 number of days were created. Start date cannot be after end date"
	 * ); } else { while (!currentDate.isAfter(generationEndDate)) { if
	 * (currentDate.getDayOfWeek() != DayOfWeek.SATURDAY &&
	 * currentDate.getDayOfWeek() != DayOfWeek.SUNDAY) { if
	 * (existingDates.contains(currentDate)) { conflictingDates.add(currentDate); }
	 * } currentDate = currentDate.plusDays(1); } } return conflictingDates; }
	 */

}
