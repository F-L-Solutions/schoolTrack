package com.FLsolutions.schoolTrack.services;

import com.FLsolutions.schoolTrack.dtos.EventCreationRequestDto;
import com.FLsolutions.schoolTrack.dtos.EventResponseDto;
import com.FLsolutions.schoolTrack.dtos.StatusResponseDto;
import com.FLsolutions.schoolTrack.exceptions.DuplicateEventException;
import com.FLsolutions.schoolTrack.exceptions.GenericEventException;
import com.FLsolutions.schoolTrack.models.Event;
import com.FLsolutions.schoolTrack.repositories.EventRepository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class EventServiceImpl implements EventService {

	private EventRepository eventRepository;

	public EventServiceImpl(EventRepository eventRepository) {
		this.eventRepository = eventRepository;
	}

	@Override
	public StatusResponseDto bulkCreateEvents(EventCreationRequestDto request) {
		StatusResponseDto responseDto = new StatusResponseDto("");

		List<Event> events = new ArrayList<>();
		LocalDate generationStartDate = request.getStartDate();
		LocalDate generationEndDate = request.getEndDate();
		LocalDate currentDate = generationStartDate;
		List<LocalDate> conflictingDates = checkExistingEvents(generationStartDate, generationEndDate);

		if (!conflictingDates.isEmpty()) {
			throw new DuplicateEventException(conflictingDates, HttpStatus.CONFLICT);
		} else {
			while (!currentDate.isAfter(generationEndDate)) {
				if (currentDate.getDayOfWeek() != DayOfWeek.SATURDAY
						&& currentDate.getDayOfWeek() != DayOfWeek.SUNDAY) {
					Event event = new Event(currentDate, request.getAvailableSpots(), request.getDayType());
					events.add(event);
				}
				currentDate = currentDate.plusDays(1);
			}
		}

		eventRepository.saveAll(events);

		responseDto.setStatus(events.size() + " number of days were created. The last created event is at "
				+ generationEndDate.toString());

		return responseDto;
	}

	private List<LocalDate> checkExistingEvents(LocalDate generationStartDate, LocalDate generationEndDate) {
		LocalDate currentDate = generationStartDate;
		List<Event> existingEvents = eventRepository.findAllByDateBetween(generationStartDate, generationEndDate);
		Set<LocalDate> existingDates = existingEvents.stream().map(Event::getDate).collect(Collectors.toSet());
		List<LocalDate> conflictingDates = new ArrayList<>();

		if (generationStartDate.isAfter(generationEndDate)) {
			throw new GenericEventException("0 number of days were created. Start date cannot be after end date",
					HttpStatus.BAD_REQUEST);
		} else {
			while (!currentDate.isAfter(generationEndDate)) {
				if (currentDate.getDayOfWeek() != DayOfWeek.SATURDAY
						&& currentDate.getDayOfWeek() != DayOfWeek.SUNDAY) {
					if (existingDates.contains(currentDate)) {
						conflictingDates.add(currentDate);
					}
				}
				currentDate = currentDate.plusDays(1);
			}
		}
		return conflictingDates;
	}

	@Override
	public List<EventResponseDto> fetchAllEvents() {
		List<Event> existingEvents = eventRepository.findAll();
		List<EventResponseDto> eventList = new ArrayList<EventResponseDto>();

		if (existingEvents.isEmpty()) {
			throw new GenericEventException("There are no events in the database.", HttpStatus.NOT_FOUND);
		}

		existingEvents.forEach(event -> eventList.add(new EventResponseDto(event)));

		return eventList;
	}

	@Override
	public EventResponseDto fetchBySysId(Long sysId) {
		
		Event event = eventRepository.findEventById(sysId).orElseThrow(
				() -> new GenericEventException("Event with this id was not found in the database.", HttpStatus.NOT_FOUND));
		
		EventResponseDto eventResponse = new EventResponseDto(event);
		return eventResponse;
	}
}
