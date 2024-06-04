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
import com.FLsolutions.schoolTrack.repositories.AttendanceRepository;
import com.FLsolutions.schoolTrack.repositories.KidRepository;
import com.FLsolutions.schoolTrack.repositories.ParentRepository;

import jakarta.transaction.Transactional;

@Service
public class KidServiceImpl implements KidService {

	private KidRepository kidRepository;
	private ParentRepository parentRepository;
	private AttendanceRepository attendanceRepository;
//	private Utils utils;

	public KidServiceImpl(KidRepository kidRepository, ParentRepository parentRepository, AttendanceRepository attendanceRepository) {
		this.kidRepository = kidRepository;
		this.parentRepository = parentRepository;
		this.attendanceRepository = attendanceRepository;
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
	@Transactional
	public StatusResponseDto createKid(KidCreationRequestDto request) {
		StatusResponseDto response = new StatusResponseDto("");

		// check if the parent exists
		Optional<Parent> optinalParent = parentRepository.findBySysId(request.getParentSysId());
		Parent parent;
		List<Parent> parentList = new ArrayList<>();

		if (optinalParent.isPresent()) {
			parent = optinalParent.get();
			parentList.add(parent);
		} else {
			throw new GenericEventException(
					"A parent with id " + request.getParentSysId() + " doesnt exist in the database.",
					HttpStatus.NOT_FOUND);
		}

		// save kid
		Kid kid = new Kid(request.getFirstName(), request.getLastName(), parentList);
		kidRepository.save(kid);
		response.setStatus("Kid was created.");

		return response;
	}

	@Override
	public List<String> fetchKidsByAttendanceDay(AttendanceDay attendanceDay) {
		List<String> resultList = new ArrayList<String>();
		Optional<List<Attendance>> existingAttendance = attendanceRepository.findByAttendanceDay(attendanceDay);
		List<Attendance> attendaces = existingAttendance.get();
		
		attendaces.forEach(attendance -> resultList.add(attendance.getKid().getFirstName() + attendance.getKid().getLastName()));
//		Optional<List<Kid>> optionalKids = kidRepository.findAllByAttendanceDay(attendanceDay);
//		List<Kid> attendingKids = new ArrayList<Kid>(); 
//		List<String> resultList = new ArrayList<String>();
//		
//		if(optionalKids.isEmpty()) {
//			throw new GenericUserException("No kids attending this day: " + attendanceDay, HttpStatus.NOT_FOUND);
//		}
//		
//		attendingKids = optionalKids.get();
//		attendingKids.forEach(kid -> resultList.add(kid.getFirstName() + " " + kid.getLastName()));
		
		
		return resultList;
	}
	
	

}
