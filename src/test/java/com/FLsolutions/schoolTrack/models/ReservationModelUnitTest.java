package com.FLsolutions.schoolTrack.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReservationModelUnitTest {

	private Reservation reservation1;
	private Reservation reservation2;
	private LocalDate date;
	private LocalDateTime createdAt;
	private DayType dayType;
	private Kid kid;

	@BeforeEach
	void setUp() {
		date = LocalDate.of(2024, 1, 1);
		createdAt = LocalDateTime.now();
		dayType = DayType.FULL_DAY;
		kid = new Kid("Test", "Kid", null);

		reservation1 = new Reservation(date, dayType, kid);
		reservation1.setSysId(1L);
		reservation1.setDate(date);
		reservation1.setDayType(dayType);
		reservation1.setCreatedAt(createdAt);
		reservation1.setKid(kid);

		reservation2 = new Reservation();
		reservation2.setSysId(2L);
		reservation2.setDate(date);
		reservation2.setDayType(dayType);
		reservation2.setCreatedAt(createdAt);
		reservation2.setKid(kid);
	}

	@Test
	void testConstructor_withValidArguments_allFieldsAreSetCorrectly() {
		assertEquals(1L, reservation1.getSysId());
		assertEquals(date, reservation1.getDate());
		assertEquals(dayType, reservation1.getDayType());
		assertEquals(createdAt, reservation1.getCreatedAt());
		assertEquals(kid, reservation1.getKid());
		assertEquals(ReservationStatus.WAITING, reservation1.getStatus());
	}

	@Test
	void testConstructor_withoutArguments_allFieldsAreSetCorrectly() {
		assertEquals(2L, reservation2.getSysId());
		assertEquals(date, reservation2.getDate());
		assertEquals(dayType, reservation2.getDayType());
		assertEquals(createdAt, reservation2.getCreatedAt());
		assertEquals(kid, reservation2.getKid());
		assertEquals(ReservationStatus.WAITING, reservation2.getStatus());
	}

	@Test
	void testGettersAndSetters_runsCorrectly() {
		Reservation reservation = new Reservation();

		reservation.setSysId(3L);
		assertEquals(3L, reservation.getSysId());

		reservation.setDate(date);
		assertEquals(date, reservation.getDate());

		reservation.setCreatedAt(createdAt);
		assertEquals(createdAt, reservation.getCreatedAt());

		reservation.setDayType(dayType);
		assertEquals(dayType, reservation.getDayType());

		reservation.setKid(kid);
		assertEquals(kid, reservation.getKid());

		reservation.setStatus(ReservationStatus.REJECTED);
		assertEquals(ReservationStatus.REJECTED, reservation.getStatus());
	}
}
