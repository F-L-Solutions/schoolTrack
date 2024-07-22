package com.FLsolutions.schoolTrack.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
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
import org.mockito.junit.jupiter.MockitoExtension;

import com.FLsolutions.schoolTrack.dtos.ReservationCreationRequestDto;
import com.FLsolutions.schoolTrack.dtos.ReservationResponseDto;
import com.FLsolutions.schoolTrack.dtos.StatusResponseDto;
import com.FLsolutions.schoolTrack.models.DayType;
import com.FLsolutions.schoolTrack.models.Event;
import com.FLsolutions.schoolTrack.models.Kid;
import com.FLsolutions.schoolTrack.models.Reservation;
import com.FLsolutions.schoolTrack.repositories.EventRepository;
import com.FLsolutions.schoolTrack.repositories.KidRepository;
import com.FLsolutions.schoolTrack.repositories.ReservationRepository;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceUnitTest {

	@Mock
	private ReservationRepository reservationRepository;

	@Mock
	private KidRepository kidRepository;

	@Mock
	private EventRepository eventRepository;

	@InjectMocks
	private ReservationServiceImpl reservationService;

	private Reservation reservation1;
	private Reservation reservation2;
	private List<Reservation> reservationList;
	private ReservationCreationRequestDto creationRequestDto;
	private Kid mockKid;
	private Event mockEvent;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);

		mockKid = new Kid();
		mockKid.setUserName("TestKid");
		mockKid.setSysId(3L);

		mockEvent = new Event();
		mockEvent.setSysId(1L);
		mockEvent.setDate(LocalDate.of(2024, 7, 22));
		mockEvent.setAvailableSpots(0);

		reservation1 = new Reservation(LocalDate.of(2024, 1, 1), DayType.FULL_DAY, mockKid);
		reservation1.setSysId(1L);
		reservation2 = new Reservation(LocalDate.of(2024, 1, 15), DayType.HALF_DAY, mockKid);
		reservation2.setSysId(2L);

		reservationList = new ArrayList<>();
		reservationList.add(reservation1);
		reservationList.add(reservation2);

		creationRequestDto = new ReservationCreationRequestDto(LocalDate.of(2024, 7, 22), DayType.FULL_DAY, "TestKid");
	}

	@Test
	void createReservation_returnsStatusResponseDto() {
		when(reservationRepository.findByKidIdAndDate(anyLong(), any())).thenReturn(Optional.empty());
		when(kidRepository.findByUserName(anyString())).thenReturn(Optional.of(mockKid));
		when(eventRepository.findByDate(any())).thenReturn(Optional.of(mockEvent));

		StatusResponseDto responseDto = reservationService.createReservation(creationRequestDto);

		verify(reservationRepository, times(1)).save(any());
		assertNotNull(responseDto);
		assertThat(responseDto.getStatus()).isEqualTo("Reservation for TestKid was created.");
	}

	@Test
	void fetchReservationBySysId_returnsReservation() {
		when(reservationRepository.findById(anyLong())).thenReturn(Optional.of(reservation1));

		ReservationResponseDto responseDto = reservationService.fetchReservationBySysId(anyLong());

		assertNotNull(responseDto);
		assertThat(responseDto.getDate()).isEqualTo(reservation1.getDate());
		assertThat(responseDto.getKid().getSysId()).isEqualTo(3L);
		assertThat(responseDto.getDayType()).isEqualTo(reservation1.getDayType());
		verify(reservationRepository, times(1)).findById(anyLong());
	}

	@Test
	void fetchAllReservations_returnsReservationsList() {
		when(reservationRepository.findAll()).thenReturn(reservationList);

		List<ReservationResponseDto> responseDtoList = reservationService.fetchAllReservations();

		assertNotNull(responseDtoList);
		assertThat(responseDtoList.get(0).getDate()).isEqualTo(reservation1.getDate());
		assertThat(responseDtoList.get(0).getDayType()).isEqualTo(reservation1.getDayType());
		assertThat(responseDtoList.get(1).getDate()).isEqualTo(reservation2.getDate());
		assertThat(responseDtoList.get(1).getDayType()).isEqualTo(reservation2.getDayType());

		verify(reservationRepository, times(1)).findAll();
	}

	@Test
	void fetchReservationsByKidSysId_returnsReservationsList() {

		when(reservationRepository.findByKidSysId(anyLong())).thenReturn(Optional.of(reservationList));

		List<ReservationResponseDto> responseDtoList = reservationService.fetchReservationsByKidSysId(anyLong());

		assertNotNull(responseDtoList);
		assertThat(responseDtoList.get(0).getDate()).isEqualTo(reservation1.getDate());
		assertThat(responseDtoList.get(0).getDayType()).isEqualTo(reservation1.getDayType());
		assertThat(responseDtoList.get(1).getDate()).isEqualTo(reservation2.getDate());
		assertThat(responseDtoList.get(1).getDayType()).isEqualTo(reservation2.getDayType());

		verify(reservationRepository, times(1)).findByKidSysId(anyLong());
	}

}
