package com.FLsolutions.schoolTrack.models;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AttendanceModelUnitTest {

	private Kid mockKid;

	@BeforeEach
	public void setUp() {
		mockKid = mock(Kid.class);
	}

	@Test
	public void testConstructor_withStatus_setsAllFieldsCorrectly() {
		LocalDate date = LocalDate.of(2024, 7, 8); // A Monday
		DayType dayType = DayType.FULL_DAY;
		AttendanceStatus status = AttendanceStatus.IDLE;

		Attendance attendance = new Attendance(date, dayType, mockKid, status);

		assertEquals(date, attendance.getDate());
		assertEquals(dayType, attendance.getDayType());
		assertEquals(mockKid, attendance.getKid());
		assertEquals(status, attendance.getAttendanceStatus());
		assertEquals(AttendanceDay.MON, attendance.getAttendanceDay());
		assertFalse(attendance.isExcused());
	}

	@Test
	public void testConstructor_withoutStatus_setsDefaultStatusAndAllFieldsCorrectly() {
		LocalDate date = LocalDate.of(2024, 7, 8); // A Monday
		DayType dayType = DayType.FULL_DAY;

		Attendance attendance = new Attendance(date, dayType, mockKid);

		assertEquals(date, attendance.getDate());
		assertEquals(dayType, attendance.getDayType());
		assertEquals(mockKid, attendance.getKid());
		assertEquals(AttendanceStatus.IDLE, attendance.getAttendanceStatus());
		assertEquals(AttendanceDay.MON, attendance.getAttendanceDay());
		assertFalse(attendance.isExcused());
	}

	@Test
	public void testSetAttendanceDay_setsCorrectDayBasedOnDate() {
		Attendance attendance = new Attendance();
		LocalDate date = LocalDate.of(2024, 7, 8); // A Monday

		attendance.setAttendanceDay(date);

		assertEquals(AttendanceDay.MON, attendance.getAttendanceDay());
	}

	@Test
	public void testIsCancelableOnTime_withFutureDate_returnsTrue() {
		LocalDate date = LocalDate.now().plusDays(2);
		if (isWeekend(date)) {
			date = date.plusDays(2);
		}

		Attendance attendance = new Attendance(date, DayType.FULL_DAY, mockKid);
		assertTrue(attendance.isCancelableOnTime());
	}

	@Test
	public void testIsCancelable_withFutureDate_returnsTrue() {
		LocalDate date = LocalDate.now().plusDays(1);
		if (isWeekend(date)) {
			date = date.plusDays(2);
		}

		Attendance attendance = new Attendance(date, DayType.FULL_DAY, mockKid);
		assertTrue(attendance.isCancelable());
	}

	@Test
	public void testIsAlreadyCanceled_withCanceledStatus_returnsTrue() {
		Attendance attendance = new Attendance();
		attendance.setAttendanceStatus(AttendanceStatus.CANCELED_ON_TIME);

		assertTrue(attendance.isAlreadyCanceled());
	}

	@Test
	public void testSetAndGetKid_returnsCorrectKid() {
		Attendance attendance = new Attendance();
		attendance.setKid(mockKid);

		assertEquals(mockKid, attendance.getKid());
	}

	@Test
	public void testSetAndGetAttendanceStatus_returnsCorrectStatus() {
		Attendance attendance = new Attendance();
		attendance.setAttendanceStatus(AttendanceStatus.ABSENT);

		assertEquals(AttendanceStatus.ABSENT, attendance.getAttendanceStatus());
	}

	@Test
	public void testSetAndGetAttendanceDay_returnsCorrectDay() {
		Attendance attendance = new Attendance();
		LocalDate date = LocalDate.of(2024, 7, 8); // A Monday

		attendance.setAttendanceDay(date);

		assertEquals(AttendanceDay.MON, attendance.getAttendanceDay());
	}

	@Test
	public void testIsAndSetExcused_returnsTrueWhenSet() {
		Attendance attendance = new Attendance();
		attendance.setExcused(true);

		assertTrue(attendance.isExcused());
	}

	@Test
	public void testSetAttendanceDayFromDate_setsCorrectDayBasedOnDate() {
		Attendance attendance = new Attendance();
		LocalDate date = LocalDate.of(2024, 7, 8); // A Monday

		attendance.setAttendanceDay(date);

		DayOfWeek dayOfWeek = date.getDayOfWeek();
		AttendanceDay expectedAttendanceDay = AttendanceDay
				.valueOf(dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.ENGLISH).toUpperCase());

		assertEquals(expectedAttendanceDay, attendance.getAttendanceDay());
	}

	@Test
	public void testIsCancelableOnTime_withPastDate_returnsFalse() {
		LocalDate date = LocalDate.now().minusDays(1);
		if (isWeekend(date)) {
			date = date.minusDays(2);
		}
		Attendance attendance = new Attendance(date, DayType.FULL_DAY, mockKid);

		assertFalse(attendance.isCancelableOnTime());
	}

	@Test
	public void testIsCancelable_withPastDate_returnsFalse() {
		LocalDate date = LocalDate.now().minusDays(1);
		if (isWeekend(date)) {
			date = date.minusDays(2);
		}
		Attendance attendance = new Attendance(date, DayType.FULL_DAY, mockKid);

		assertFalse(attendance.isCancelable());
	}

	@Test
	public void testIsAlreadyCanceled_withNonCanceledStatus_returnsFalse() {
		Attendance attendance = new Attendance();
		attendance.setAttendanceStatus(AttendanceStatus.ATTENDED);

		assertFalse(attendance.isAlreadyCanceled());
	}

	@Test
	public void testCreateAttendanceOnWeekend_throwsIllegalArgumentException() {
		LocalDate sunday = LocalDate.of(2024, 6, 23); // A Sunday
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			new Attendance(sunday, DayType.FULL_DAY, mockKid);
		});

		assertEquals("Attendance cannot be created on weekends", exception.getMessage());
	}

	// helper method for checking if date is on weekend
	private boolean isWeekend(LocalDate date) {
		DayOfWeek dayOfWeek = date.getDayOfWeek();
		return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
	}

}
