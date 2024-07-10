package com.FLsolutions.schoolTrack.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.FLsolutions.schoolTrack.dtos.EventCreationRequestDto;
import com.FLsolutions.schoolTrack.dtos.EventResponseDto;
import com.FLsolutions.schoolTrack.dtos.StatusResponseDto;
import com.FLsolutions.schoolTrack.models.DayType;
import com.FLsolutions.schoolTrack.models.Event;
import com.FLsolutions.schoolTrack.services.EventService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@ContextConfiguration
@WebMvcTest(EventController.class)
public class EventControllerUnitTest {

	@MockBean
	private EventService eventService;

	@Autowired
	private MockMvc mockMvc;

	private ObjectMapper objectMapper;

	EventResponseDto eventResponseDto1;
	EventResponseDto eventResponseDto2;
	private List<EventResponseDto> responseDtoList;
	private EventCreationRequestDto eventCreationRequestDto;

	@BeforeEach
	void setup() {
		// Initialise ObjectMapper instance
		objectMapper = new ObjectMapper();
		// Register the JavaTimeModule to handle Java 8 date/time types
		objectMapper.registerModule(new JavaTimeModule());

		Event event1 = new Event(LocalDate.of(2024, 1, 1), 10, DayType.FULL_DAY);
		Event event2 = new Event(LocalDate.of(2024, 1, 2), 15, DayType.HALF_DAY);
		event1.setSysId(1L);
		event2.setSysId(2L);

		eventResponseDto1 = new EventResponseDto(event1);
		eventResponseDto2 = new EventResponseDto(event2);

		responseDtoList = new ArrayList<EventResponseDto>();
		responseDtoList.add(eventResponseDto1);
		responseDtoList.add(eventResponseDto2);

		eventCreationRequestDto = new EventCreationRequestDto(10, DayType.FULL_DAY, LocalDate.of(2024, 1, 1),
				LocalDate.of(2024, 1, 15));
	}

	@Test
	void can_create_events_in_bulk() throws Exception {
		StatusResponseDto responseDto = new StatusResponseDto("ok");

		Mockito.when(eventService.bulkCreateEvents(Mockito.any())).thenReturn(responseDto);

		mockMvc.perform(MockMvcRequestBuilders.post("/events/bulk-create")
				.content(objectMapper.writeValueAsString(eventCreationRequestDto))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.status", is("ok")));
	}

	@Test
	void can_get_all_events() throws Exception {
		Mockito.when(eventService.fetchAllEvents()).thenReturn(responseDtoList);

		mockMvc.perform(MockMvcRequestBuilders.get("/events")).andExpect(status().isOk())
				.andExpect(jsonPath("$.[0].sysId", is(1)))
				.andExpect(jsonPath("$.[0].date", is("2024-01-01")))
				.andExpect(jsonPath("$.[0].availableSpots", is(10)))
				.andExpect(jsonPath("$.[0].dayType", is("FULL_DAY")))
				.andExpect(jsonPath("$.[1].sysId", is(2)))
				.andExpect(jsonPath("$.[1].date", is("2024-01-02")))
				.andExpect(jsonPath("$.[1].availableSpots", is(15)))
				.andExpect(jsonPath("$.[1].dayType", is("HALF_DAY")));
	}
	
	@Test
	void can_get_event_by_id() throws Exception {
		Mockito.when(eventService.fetchBySysId(1L)).thenReturn(eventResponseDto1);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/events/1")).andExpect(status().isOk())
		.andExpect(jsonPath("$.sysId", is(1)))
		.andExpect(jsonPath("$.date", is("2024-01-01")))
		.andExpect(jsonPath("$.availableSpots", is(10)))
		.andExpect(jsonPath("$.dayType", is("FULL_DAY")));
	}

}
