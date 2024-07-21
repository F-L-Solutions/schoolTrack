package com.FLsolutions.schoolTrack.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
import com.FLsolutions.schoolTrack.exceptions.DuplicateEventException;
import com.FLsolutions.schoolTrack.exceptions.GenericEventException;
import com.FLsolutions.schoolTrack.models.DayType;
import com.FLsolutions.schoolTrack.models.Event;
import com.FLsolutions.schoolTrack.repositories.EventRepository;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

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

		assertNotNull(responseDtoList);
		assertThat(responseDtoList).hasSize(2);
		assertThat(responseDtoList.get(0).getSysId()).isEqualTo(1L);
		assertThat(responseDtoList.get(1).getSysId()).isEqualTo(2L);
	}

	@Test
	void fetchBySysId_returnsEventResponseDto() {
		when(eventRepository.findEventById(any())).thenReturn(Optional.of(event1));

		EventResponseDto eventResponse = eventService.fetchBySysId(anyLong());

		assertNotNull(eventResponse);
		assertThat(eventResponse.getSysId()).isEqualTo(1L);
		assertThat(eventResponse.getDate()).isEqualTo(LocalDate.of(2024, 1, 1));
		assertThat(eventResponse.getAvailableSpots()).isEqualTo(10);
		assertThat(eventResponse.getDayType()).isEqualTo(DayType.FULL_DAY);
	}

	@Test
	void bulkCreateEvents_withExistingEvents_throwsDuplicateEventException() {
		when(eventRepository.findAllByDateBetween(eventCreationRequestDto.getStartDate(),
				eventCreationRequestDto.getEndDate())).thenReturn(eventList);

		DuplicateEventException exception = assertThrows(DuplicateEventException.class, () -> {
			eventService.bulkCreateEvents(eventCreationRequestDto);
		});

		assertNotNull(exception);
		assertEquals(HttpStatus.CONFLICT, exception.getStatus());
		assertEquals(
				"Bulk creation of events failed. Events already exist for these specified dates: [2024-01-01, 2024-01-02]",
				exception.getMessage());
	}

	@Test
	void fetchAllEvents_whenNoEventsExist_throwsGenericEventException() {
		when(eventRepository.findAll()).thenReturn(new ArrayList<>());

		GenericEventException exception = assertThrows(GenericEventException.class, () -> {
			eventService.fetchAllEvents();
		});

		assertNotNull(exception);
		assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
		assertEquals("There are no events in the database.", exception.getMessage());
	}

	@Test
	void fetchBySysId_withInvalidId_throwsGenericEventException() {
		when(eventRepository.findEventById(anyLong())).thenReturn(Optional.empty());

		GenericEventException exception = assertThrows(GenericEventException.class, () -> {
			eventService.fetchBySysId(999L);
		});

		assertNotNull(exception);
		assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
		assertEquals("Event with this id was not found in the database.", exception.getMessage());
	}

	@Test
	void bulkCreateEvents_withStartDateAfterEndDate_throwsGenericEventException() {
		EventCreationRequestDto invalidDto = new EventCreationRequestDto(10, DayType.FULL_DAY,
				LocalDate.of(2024, 1, 15), LocalDate.of(2024, 1, 1));

		GenericEventException exception = assertThrows(GenericEventException.class, () -> {
			eventService.bulkCreateEvents(invalidDto);
		});

		assertNotNull(exception);
		assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
		assertEquals("0 number of days were created. Start date cannot be after end date", exception.getMessage());
	}
}
