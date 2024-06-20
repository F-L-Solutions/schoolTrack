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
import com.FLsolutions.schoolTrack.exceptions.GenericAttendanceException;
import com.FLsolutions.schoolTrack.exceptions.GenericEventException;
import com.FLsolutions.schoolTrack.exceptions.KidNotFoundException;
import com.FLsolutions.schoolTrack.models.Attendance;
import com.FLsolutions.schoolTrack.models.AttendanceDay;
import com.FLsolutions.schoolTrack.models.AttendanceStatus;
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
	private SubstituteCreditService substituteCreditService;

	public AttendanceServiceImpl(AttendanceRepository attendanceRepository, KidRepository kidRepository,
			EventRepository eventRepository, SubstituteCreditService substituteCreditService) {
		this.attendanceRepository = attendanceRepository;
		this.kidRepository = kidRepository;
		this.eventRepository = eventRepository;
		this.substituteCreditService = substituteCreditService;
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

		Event existingEvent = eventRepository.findByDate(request.getDate())
				.orElseThrow(() -> new GenericEventException("For selected date there was no event found in database",
						HttpStatus.NOT_FOUND));

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

	public StatusResponseDto bulkCreateAttendances(AttendanceCreationRequestDto request) {
	    StatusResponseDto response = new StatusResponseDto("");
	    List<Attendance> attendances = new ArrayList<>();

	    Kid kid = kidRepository.findByUserName(request.getKidUserName())
	            .orElseThrow(() -> new KidNotFoundException("Selected kid username was not found in the database",
	                    HttpStatus.NOT_FOUND));

	    LocalDate currentDate = request.getStartDate();
	    
	    Set<DayOfWeek> attendanceDaysOfWeek = request.getAttendanceDays().stream()
	            .map(AttendanceDay::getDayOfWeek)
	            .collect(Collectors.toSet());
	    
	    while (!currentDate.isAfter(request.getEndDate())) {
	        if (attendanceDaysOfWeek.contains(currentDate.getDayOfWeek())) {
	            LocalDate finalCurrentDate = currentDate;
	            Optional<Attendance> existingAttendance = attendanceRepository.findByKidIdAndDate(kid.getSysId(),
	                    currentDate);
	            
	            if (existingAttendance.isPresent()) {
	                throw new DuplicateAttendanceException("Selected kid already has attendance for " + currentDate,
	                        HttpStatus.CONFLICT);
	            }

	            Event existingEvent = eventRepository.findByDate(currentDate)
	                    .orElseThrow(() -> new GenericEventException(
	                            "For selected date " + finalCurrentDate.toString() + " there was no event found in database",
	                            HttpStatus.NOT_FOUND));

	            if (existingEvent.getAvailableSpots() == 0) {
	                throw new GenericEventException(
	                        "For selected date " + currentDate + " there are no more spots available",
	                        HttpStatus.BAD_REQUEST);
	            }

	            // Decrease available spots in event by 1
	            int newlyAvailableSpots = existingEvent.getAvailableSpots() - 1;
	            existingEvent.setAvailableSpots(newlyAvailableSpots);
	            eventRepository.save(existingEvent);

	            // Create new attendance
	            Attendance attendance = new Attendance(currentDate, request.getDayType(), kid);
	            attendances.add(attendance);
	        }
	        currentDate = currentDate.plusDays(1);
	    }
	    
	    // Save all attendances
	    attendanceRepository.saveAll(attendances);

	    response.setStatus(attendances.size() + " attendances were created for " + request.getKidUserName());
	    return response;
	}

	public StatusResponseDto cancelAttendance(Long attendanceId) {
		StatusResponseDto response = new StatusResponseDto("");

		Attendance attendance = attendanceRepository.findById(attendanceId)
				.orElseThrow(() -> new GenericAttendanceException("Attendance not found with id: " + attendanceId,
						HttpStatus.NOT_FOUND));

		if (!attendance.isCancelable() || attendance.isAlreadyCanceled()) {
			throw new GenericAttendanceException("Attendance is not possible to cancel anymore.",
					HttpStatus.BAD_REQUEST);
		} else {
			// if cancelled late (less than 24h before the event), no substitute credit will
			// be granted
			if (!attendance.isCancelableOnTime()) {
				attendance.setAttendanceStatus(AttendanceStatus.CANCELED_LATE);
				attendanceRepository.save(attendance);
				response.setStatus("Attendance canceled late, Substitute Credit was not granted.");
			} else {
				// if cancelled at least 24h before the event, substitute credit is granted (the
				// time can be changed in Attendance model)
				attendance.setAttendanceStatus(AttendanceStatus.CANCELED_ON_TIME);
				attendance.setExcused(true);
				attendanceRepository.save(attendance);

				substituteCreditService.createSubstituteCredit(attendance.getKid());
				response.setStatus("Attendance canceled and Substitute Credit granted successfully.");
			}
		}
		return response;
	}
}
