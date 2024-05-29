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
import com.FLsolutions.schoolTrack.exceptions.DuplicateAttendanceException;
import com.FLsolutions.schoolTrack.exceptions.GenericEventException;
import com.FLsolutions.schoolTrack.exceptions.KidNotFoundException;
import com.FLsolutions.schoolTrack.models.Attendance;
import com.FLsolutions.schoolTrack.models.AttendanceStatus;
import com.FLsolutions.schoolTrack.models.DayType;
import com.FLsolutions.schoolTrack.models.Event;
import com.FLsolutions.schoolTrack.models.Kid;
import com.FLsolutions.schoolTrack.repositories.AttendanceRepository;
import com.FLsolutions.schoolTrack.repositories.EventRepository;
import com.FLsolutions.schoolTrack.repositories.KidRepository;

@Service
public class AttendanceServiceImpl implements AttendanceService {

	private AttendanceRepository attendanceRepository;
	private KidRepository kidRepository;
	private EventRepository eventRepository;

	public AttendanceServiceImpl(AttendanceRepository attendanceRepository, KidRepository kidRepository,
			EventRepository eventRepository) {
		this.attendanceRepository = attendanceRepository;
		this.kidRepository = kidRepository;
		this.eventRepository = eventRepository;
	}

	public StatusResponseDto createAttendance(AttendanceCreationRequestDto request) {
		StatusResponseDto response = new StatusResponseDto("");

		Kid kid = kidRepository.findByUserName(request.getKidUserName())
				.orElseThrow(() -> new KidNotFoundException("Selected kid username was not found in the database",
						HttpStatus.NOT_FOUND));

		Optional<Attendance> existingAttendance = attendanceRepository.findByKidIdAndDate(kid.getSysId(),
				request.getDate());

		if (existingAttendance.isPresent()) {
			throw new DuplicateAttendanceException("Selected kid already has attendance for this day",
					HttpStatus.CONFLICT);
		}

		Event existingEvent = eventRepository.findByDate(request.getDate()).orElseThrow(
				() -> new GenericEventException("For selected date there was no event found in database", HttpStatus.NOT_FOUND));

		if (existingEvent.getAvailableSpots() == 0) {
			throw new GenericEventException("For selected date there are no more spots available",
					HttpStatus.BAD_REQUEST);
		}

		// decrease available spots in event by 1
		int newlyAvailableSpots = existingEvent.getAvailableSpots() - 1;
		existingEvent.setAvailableSpots(newlyAvailableSpots);
		eventRepository.save(existingEvent);

		// save new attendance
		Attendance attendance = new Attendance(request.getDate(), request.getDayType(), kid);
		attendanceRepository.save(attendance);
		response.setStatus("Attendance for " + request.getKidUserName() + " was created.");

		return response;
	}

	public StatusResponseDto bulkCreateAttendances(AttendanceCreationRequestDto dto) {
		StatusResponseDto response = new StatusResponseDto("");
		return response;
	}

}
