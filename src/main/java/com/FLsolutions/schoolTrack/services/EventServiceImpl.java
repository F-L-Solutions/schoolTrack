package com.FLsolutions.schoolTrack.services;
import com.FLsolutions.schoolTrack.repositories.EventRepository;
import org.springframework.stereotype.Service;

@Service
public class EventServiceImpl implements EventService{
	
	private EventRepository eventRepository;
	
	public EventServiceImpl(EventRepository eventRepository) {
		this.eventRepository= eventRepository;
	}
	
}
