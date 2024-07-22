package com.FLsolutions.schoolTrack.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EventModelUnitTest {

	private Event event1;
	private Event event2;
	private Event event3;
	private LocalDate date;
	private LocalDateTime createdAt;
	private int availableSpots;
	private DayType dayType;

	@BeforeEach
	void setUp() {
		date = LocalDate.of(2024, 1, 1);
		createdAt = LocalDateTime.now();
		availableSpots = 10;
		dayType = DayType.FULL_DAY;

		event1 = new Event(date, availableSpots, dayType);
		event1.setSysId(1L);
		event1.setCreatedAt(createdAt);

		event2 = new Event(date, dayType);
		event2.setSysId(2L);
		event2.setCreatedAt(createdAt);

		event3 = new Event();
		event3.setDate(date);
		event3.setAvailableSpots(availableSpots);
		event3.setDayType(dayType);
		event3.setSysId(3L);
		event3.setCreatedAt(createdAt);
	}

	@Test
	void testConstructor_WithAvailableSpots_allFieldsAreSetCorrectly() {
		assertEquals(date, event1.getDate());
		assertEquals(availableSpots, event1.getAvailableSpots());
		assertEquals(dayType, event1.getDayType());
		assertEquals(1L, event1.getSysId());
		assertEquals(createdAt, event1.getCreatedAt());
	}

	@Test
	void testContructor_withoutAvailableSpots_allFieldsAreSetCorrectly() {
		assertEquals(date, event2.getDate());
		assertEquals(dayType, event2.getDayType());
		assertEquals(2L, event2.getSysId());
		assertEquals(createdAt, event2.getCreatedAt());
	}

	@Test
	void testDefaultContrustor_allFieldsAreSetCorrectly() {
		assertEquals(date, event3.getDate());
		assertEquals(availableSpots, event3.getAvailableSpots());
		assertEquals(dayType, event3.getDayType());
		assertEquals(3L, event3.getSysId());
		assertEquals(createdAt, event3.getCreatedAt());
	}

	@Test
	void testGettersAndSetters_runsCorrectly() {
		Event event = new Event();

		event.setDate(date);
		assertEquals(date, event.getDate());

		event.setAvailableSpots(availableSpots);
		assertEquals(availableSpots, event.getAvailableSpots());

		event.setDayType(dayType);
		assertEquals(dayType, event.getDayType());

		event.setSysId(4L);
		assertEquals(4L, event.getSysId());

		event.setCreatedAt(createdAt);
		assertEquals(createdAt, event.getCreatedAt());
	}

}
