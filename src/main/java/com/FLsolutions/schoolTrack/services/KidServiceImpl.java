package com.FLsolutions.schoolTrack.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.FLsolutions.schoolTrack.dtos.KidCreationRequestDto;
import com.FLsolutions.schoolTrack.dtos.KidResponseDto;
import com.FLsolutions.schoolTrack.dtos.StatusResponseDto;
import com.FLsolutions.schoolTrack.exceptions.GenericEventException;
import com.FLsolutions.schoolTrack.exceptions.GenericUserException;

import com.FLsolutions.schoolTrack.models.Attendance;
import com.FLsolutions.schoolTrack.models.AttendanceDay;

import com.FLsolutions.schoolTrack.models.Kid;
import com.FLsolutions.schoolTrack.models.Parent;
import com.FLsolutions.schoolTrack.models.Event;
import com.FLsolutions.schoolTrack.repositories.AttendanceRepository;
import com.FLsolutions.schoolTrack.repositories.EventRepository;
import com.FLsolutions.schoolTrack.repositories.KidRepository;
import com.FLsolutions.schoolTrack.repositories.ParentRepository;

@Service
public class KidServiceImpl implements KidService {

	private KidRepository kidRepository;
	private ParentRepository parentRepository;
	private AttendanceRepository attendanceRepository;
	private EventRepository eventRepository;
//	private Utils utils;

	public KidServiceImpl(KidRepository kidRepository, ParentRepository parentRepository,
			AttendanceRepository attendanceRepository, EventRepository eventRepository) {
		this.kidRepository = kidRepository;
		this.parentRepository = parentRepository;
		this.attendanceRepository = attendanceRepository;
		this.eventRepository = eventRepository;
	}

	@Override
	public List<KidResponseDto> getAllKids() {
		List<Kid> kids = kidRepository.findAll();
		return kids.stream().map(kid -> new KidResponseDto(kid)).collect(Collectors.toList());
	}

	@Override
	public KidResponseDto fetchKidBySysId(Long sysId) {
		Optional<Kid> optinalKid = kidRepository.findBySysId(sysId);

		if (optinalKid.isPresent()) {
			return new KidResponseDto(optinalKid.get());
		} else
			throw new GenericEventException("A kid with id " + sysId + " doesnt exist.", HttpStatus.NOT_FOUND);
	}

	@Override
	public StatusResponseDto createKid(KidCreationRequestDto request) {
		StatusResponseDto response = new StatusResponseDto("");

		// Check if the parent exists
		Optional<Parent> optionalParent = parentRepository.findBySysId(request.getParentSysId());
		Parent parent;
		List<Parent> parentList = new ArrayList<>();

		if (optionalParent.isPresent()) {
			parent = optionalParent.get();
			parentList.add(parent);
		} else {
			throw new GenericUserException(
					"A parent with id " + request.getParentSysId() + " doesn't exist in the database.",
					HttpStatus.NOT_FOUND);
		}

		// Check if a kid with the same first and last name exists
		Optional<Kid> existingKid = kidRepository.findByFirstNameAndLastName(request.getFirstName(),
				request.getLastName());

		if (existingKid.isPresent()) {
			throw new GenericUserException(
					"A kid with the name " + request.getFirstName() + " " + request.getLastName() + " already exists.",
					HttpStatus.CONFLICT);
		}

		// Save kid
		Kid kid = new Kid(request.getFirstName(), request.getLastName(), parentList);
		kidRepository.save(kid);
		
	    parent.getKids().add(kid);
	    parentRepository.save(parent);
	    
		response.setStatus("Kid was created.");
		return response;
	}

	@Override
	public List<String> fetchKidsByAttendanceDay(AttendanceDay attendanceDay) {
		List<String> resultList = new ArrayList<String>();
		Optional<List<Attendance>> existingAttendance = attendanceRepository.findByAttendanceDay(attendanceDay);
		List<Attendance> attendaces = existingAttendance.get();

		attendaces.forEach(attendance -> resultList
				.add(attendance.getKid().getFirstName() + " " + attendance.getKid().getLastName()));
		return resultList;
	}

	@Override
	public List<KidResponseDto> fetchKidsByParentId(Long parentId) {

		// Parent Existence Check
		Optional<Parent> parentOptional = parentRepository.findBySysId(parentId);
		if (parentOptional.isEmpty()) {
			throw new GenericUserException("Parent with ID " + parentId + " not found.", HttpStatus.NOT_FOUND);
		}

		List<KidResponseDto> resultList = new ArrayList<>();
		Optional<List<Kid>> existingKids = kidRepository.findByParentId(parentId);

		if (existingKids.isEmpty() || existingKids.get().isEmpty()) {
			throw new GenericUserException("No kids found for parent ID " + parentId + ".", HttpStatus.NOT_FOUND);
		}

		List<Kid> kids = existingKids.get();
		kids.forEach(kid -> resultList.add(new KidResponseDto(kid)));

		return resultList;
	}

	@Override
	public List<KidResponseDto> fetchKidsByAttendanceDate(LocalDate date) {
		List<KidResponseDto> resultList = new ArrayList<>();

		// Check if an event exists for the given date
		Optional<Event> existingEvent = eventRepository.findByDate(date);
		if (existingEvent.isEmpty()) {
			throw new GenericEventException("An event for this date doesn't exist: " + date.toString(),
					HttpStatus.NOT_FOUND);
		}

		Optional<List<Kid>> existingKids = kidRepository.findByAttendanceDate(date);

		// Check if any kids are found for the given date
		if (existingKids.isEmpty() || existingKids.get().isEmpty()) {
			throw new GenericEventException("No kids found for the attendance date: " + date.toString(),
					HttpStatus.NOT_FOUND);
		}

		existingKids.get().forEach(kid -> resultList.add(new KidResponseDto(kid)));

		return resultList;
	}

}
