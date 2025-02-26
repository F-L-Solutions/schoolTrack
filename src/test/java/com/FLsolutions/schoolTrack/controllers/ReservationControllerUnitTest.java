package com.FLsolutions.schoolTrack.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.mock;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.FLsolutions.schoolTrack.dtos.ReservationCreationRequestDto;
import com.FLsolutions.schoolTrack.dtos.ReservationResponseDto;
import com.FLsolutions.schoolTrack.dtos.StatusResponseDto;
import com.FLsolutions.schoolTrack.exceptions.DuplicateReservationException;
import com.FLsolutions.schoolTrack.exceptions.GenericEventException;
import com.FLsolutions.schoolTrack.exceptions.GenericReservationException;
import com.FLsolutions.schoolTrack.exceptions.KidNotFoundException;
import com.FLsolutions.schoolTrack.filters.JwtAuthenticationFilter;
import com.FLsolutions.schoolTrack.models.DayType;
import com.FLsolutions.schoolTrack.models.Kid;
import com.FLsolutions.schoolTrack.models.Reservation;
import com.FLsolutions.schoolTrack.services.ReservationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@ContextConfiguration
@WebMvcTest(ReservationController.class)
public class ReservationControllerUnitTest {

	@MockBean
	private ReservationService reservarionService;
	
	@MockBean
	private JwtAuthenticationFilter jwtAuthenticationFilter;

	@Autowired
	private MockMvc mockMvc;

	private ObjectMapper objectMapper;

	private ReservationResponseDto reservationResponseDto1;
	private ReservationResponseDto reservationResponseDto2;
	private List<ReservationResponseDto> responseDtoList;
	private ReservationCreationRequestDto reservationRequestDto;

	private Kid mockKid;

	@BeforeEach
	void setup() {

		// Initialise ObjectMapper instance
		objectMapper = new ObjectMapper();
		// Register the JavaTimeModule to handle Java 8 date/time types
		objectMapper.registerModule(new JavaTimeModule());

		// Mock Kid class
		mockKid = mock(Kid.class);

		Reservation reservation1 = new Reservation(LocalDate.of(2024, 1, 1), DayType.FULL_DAY, mockKid);
		Reservation reservation2 = new Reservation(LocalDate.of(2024, 1, 2), DayType.HALF_DAY, mockKid);

		reservation1.setSysId(1L);
		reservation2.setSysId(2L);

		reservationResponseDto1 = new ReservationResponseDto(reservation1);
		reservationResponseDto2 = new ReservationResponseDto(reservation2);

		responseDtoList = new ArrayList<ReservationResponseDto>();
		responseDtoList.add(reservationResponseDto1);
		responseDtoList.add(reservationResponseDto2);

		reservationRequestDto = new ReservationCreationRequestDto(LocalDate.of(2024, 1, 3), DayType.FULL_DAY,
				mockKid.getUsername());
	}

	@Test
	void POST_create_returnsOkStatus() throws Exception {
		StatusResponseDto responseDto = new StatusResponseDto("ok");

		Mockito.when(reservarionService.createReservation(Mockito.any())).thenReturn(responseDto);

		mockMvc.perform(MockMvcRequestBuilders.post("/reservations")
				.content(objectMapper.writeValueAsString(reservationRequestDto))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.status", is("ok")));
	}

	@Test
	void GET_getReservationBySysId_returnsReservation() throws Exception {
		Mockito.when(reservarionService.fetchReservationBySysId(1L)).thenReturn(reservationResponseDto1);

		mockMvc.perform(MockMvcRequestBuilders.get("/reservations/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.sysId", is(1)))
				.andExpect(jsonPath("$.date", is("2024-01-01")))
				.andExpect(jsonPath("$.dayType", is("FULL_DAY")))
				.andExpect(jsonPath("$.status", is("WAITING")));
	}

	@Test
	void GET_getAllReservations_returnsReservationsList() throws Exception {
		Mockito.when(reservarionService.fetchAllReservations()).thenReturn(responseDtoList);

		mockMvc.perform(MockMvcRequestBuilders.get("/reservations"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.[0].sysId", is(1)))
				.andExpect(jsonPath("$.[0].date", is("2024-01-01")))
				.andExpect(jsonPath("$.[0].dayType", is("FULL_DAY")))
				.andExpect(jsonPath("$.[0].status", is("WAITING")))
				.andExpect(jsonPath("$.[1].sysId", is(2)))
				.andExpect(jsonPath("$.[1].date", is("2024-01-02")))
				.andExpect(jsonPath("$.[1].dayType", is("HALF_DAY")))
				.andExpect(jsonPath("$.[1].status", is("WAITING")));
	}

	@Test
	void GET_getReservationsByKidId_returnsReservation() throws Exception {
		Mockito.when(reservarionService.fetchReservationsByKidSysId(Mockito.any())).thenReturn(responseDtoList);

		mockMvc.perform(MockMvcRequestBuilders.get("/reservations/kid/500"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.[0].sysId", is(1)))
				.andExpect(jsonPath("$.[0].date", is("2024-01-01")))
				.andExpect(jsonPath("$.[0].dayType", is("FULL_DAY")))
				.andExpect(jsonPath("$.[0].status", is("WAITING")))
				.andExpect(jsonPath("$.[1].sysId", is(2)))
				.andExpect(jsonPath("$.[1].date", is("2024-01-02")))
				.andExpect(jsonPath("$.[1].dayType", is("HALF_DAY")))
				.andExpect(jsonPath("$.[1].status", is("WAITING")));
	}

	@Test
	void GET_getReservationBySysId_withInvalidId_returnsError() throws Exception {
		Mockito.when(reservarionService.fetchReservationBySysId(Mockito.anyLong()))
				.thenThrow(new GenericReservationException("Validation Failed", HttpStatus.NOT_FOUND));

		mockMvc.perform(MockMvcRequestBuilders.get("/reservations/987"))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.message", is("Validation Failed")));
	}

	@Test
	void POST_create_withInvalidPayload_returnsError() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.post("/reservations").content("{\"invalid\": \"data\""))
				.andExpect(status().isUnsupportedMediaType());
	}

	@Test
	void GET_getAllReservations_whenNoReservationExists_returnsError() throws Exception {
		Mockito.when(reservarionService.fetchAllReservations())
				.thenThrow(new GenericReservationException("Validation Failed", HttpStatus.NOT_FOUND));

		mockMvc.perform(MockMvcRequestBuilders.get("/reservations"))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.message", is("Validation Failed")));
	}

	@Test
	void POST_create_withDuplicateEntries_returnsError() throws Exception {
		Mockito.when(reservarionService.createReservation(Mockito.any()))
				.thenThrow(new DuplicateReservationException("Validation Failed", HttpStatus.CONFLICT));

		mockMvc.perform(MockMvcRequestBuilders.post("/reservations")
				.content(objectMapper.writeValueAsString(reservationRequestDto))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isConflict())
				.andExpect(jsonPath("$.message", is("Validation Failed")))
				.andExpect(jsonPath("$.status", is(409)));
	}

	@Test
	void POST_create_withInvalidKidUsername_returnsError() throws Exception {
		Mockito.when(reservarionService.createReservation(Mockito.any()))
				.thenThrow(new KidNotFoundException("Validation Failed", HttpStatus.NOT_FOUND));

		mockMvc.perform(MockMvcRequestBuilders.post("/reservations")
				.content(objectMapper.writeValueAsString(reservationRequestDto))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.message", is("Validation Failed")))
				.andExpect(jsonPath("$.status", is(404)));
	}

	@Test
	void POST_create_withInvalidDate_returnsError() throws Exception {
		Mockito.when(reservarionService.createReservation(Mockito.any()))
				.thenThrow(new GenericEventException("Validation Failed", HttpStatus.NOT_FOUND));

		mockMvc.perform(MockMvcRequestBuilders.post("/reservations")
				.content(objectMapper.writeValueAsString(reservationRequestDto))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.message", is("Validation Failed")))
				.andExpect(jsonPath("$.status", is(404)));
	}

	@Test
	void POST_create_withUnavailableSpots_returnsError() throws Exception {
		Mockito.when(reservarionService.createReservation(Mockito.any()))
				.thenThrow(new GenericEventException("Validation Failed", HttpStatus.CONFLICT));

		mockMvc.perform(MockMvcRequestBuilders.post("/reservations")
				.content(objectMapper.writeValueAsString(reservationRequestDto))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isConflict())
				.andExpect(jsonPath("$.message", is("Validation Failed")))
				.andExpect(jsonPath("$.status", is(409)));
	}

	@Test
	void GET_getReservationsByKidId_withInvalidKidId_returnsError() throws Exception {

		Mockito.when(reservarionService.fetchReservationsByKidSysId(Mockito.anyLong()))
				.thenThrow(new GenericReservationException("Validation Failed", HttpStatus.NOT_FOUND));

		mockMvc.perform(MockMvcRequestBuilders.get("/reservations/kid/999"))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.message", is("Validation Failed")))
				.andExpect(jsonPath("$.status", is(404)));

	}

}
