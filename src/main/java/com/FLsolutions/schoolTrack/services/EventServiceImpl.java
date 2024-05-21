package com.FLsolutions.schoolTrack.services;

import com.FLsolutions.schoolTrack.dtos.EventCreationRequestDto;
import com.FLsolutions.schoolTrack.dtos.StatusResponseDto;
import com.FLsolutions.schoolTrack.models.Event;
import com.FLsolutions.schoolTrack.repositories.EventRepository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class EventServiceImpl implements EventService {

	private EventRepository eventRepository;

	public EventServiceImpl(EventRepository eventRepository) {
		this.eventRepository = eventRepository;
	}
	
	public StatusResponseDto bulkCreateEvents(EventCreationRequestDto request) {
		StatusResponseDto responseDto = new StatusResponseDto("");
		List<Event> events = new ArrayList<>();
        LocalDate currentDate = request.getStartDate();
        LocalDate generationEndDate = request.getEndDate();
        
        while(!currentDate.isAfter(generationEndDate)) {
        	if(currentDate.getDayOfWeek() != DayOfWeek.SATURDAY && currentDate.getDayOfWeek() != DayOfWeek.SUNDAY) {
                Event event = new Event(currentDate, request.getAvailableSpots(), request.getDayType());
                events.add(event);
        	}
        	currentDate = currentDate.plusDays(1);
        }
        
        eventRepository.saveAll(events);
        
        responseDto.setStatus(events.size() + " number of days were created. The last created event is at " + generationEndDate.toString());
        
        return responseDto;
	}

}
