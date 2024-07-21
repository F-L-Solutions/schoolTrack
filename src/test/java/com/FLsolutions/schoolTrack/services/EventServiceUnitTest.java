package com.FLsolutions.schoolTrack.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.FLsolutions.schoolTrack.dtos.EventCreationRequestDto;
import com.FLsolutions.schoolTrack.dtos.EventResponseDto;
import com.FLsolutions.schoolTrack.dtos.StatusResponseDto;
import com.FLsolutions.schoolTrack.models.DayType;
import com.FLsolutions.schoolTrack.models.Event;
import com.FLsolutions.schoolTrack.repositories.EventRepository;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class EventServiceUnitTest {

	@Mock
	private EventRepository eventRepository;

	@InjectMocks
	private EventServiceImpl eventService;

	private Event event1;
	private Event event2;
	private List<Event> eventList;
	private EventCreationRequestDto eventCreationRequestDto;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);

		event1 = new Event(LocalDate.of(2024, 1, 1), 10, DayType.FULL_DAY);
		event1.setSysId(1L);
		event2 = new Event(LocalDate.of(2024, 1, 2), 15, DayType.HALF_DAY);
		event2.setSysId(2L);

		eventList = new ArrayList<>();
		eventList.add(event1);
		eventList.add(event2);

		eventCreationRequestDto = new EventCreationRequestDto(10, DayType.FULL_DAY, LocalDate.of(2024, 1, 1),
				LocalDate.of(2024, 1, 15));
	}

	@Test
	void bulkCreateEvents_returnsStatusResponseDto() {
		when(eventRepository.findAllByDateBetween(any(), any())).thenReturn(new ArrayList<>());

		StatusResponseDto responseDto = eventService.bulkCreateEvents(eventCreationRequestDto);

		verify(eventRepository, times(1)).saveAll(anyList());
		assertNotNull(responseDto);
		assertThat(responseDto.getStatus())
				.isEqualTo("11 number of days were created. The last created event is at 2024-01-15");
	}

	@Test
	void fetchAllEvents_returnsEventResponseDtoList() {
		when(eventRepository.findAll()).thenReturn(eventList);

		List<EventResponseDto> responseDtoList = eventService.fetchAllEvents();

		assertThat(responseDtoList).hasSize(2);
		assertThat(responseDtoList.get(0).getSysId()).isEqualTo(1L);
		assertThat(responseDtoList.get(1).getSysId()).isEqualTo(2L);
	}

	@Test
	void fetchBySysId_returnsEventResponseDto() {
		when(eventRepository.findEventById(any())).thenReturn(Optional.of(event1));

		EventResponseDto eventResponse = eventService.fetchBySysId(anyLong());

		assertThat(eventResponse.getSysId()).isEqualTo(1L);
		assertThat(eventResponse.getDate()).isEqualTo(LocalDate.of(2024, 1, 1));
		assertThat(eventResponse.getAvailableSpots()).isEqualTo(10);
		assertThat(eventResponse.getDayType()).isEqualTo(DayType.FULL_DAY);
	}

}
