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
    public void testAttendanceConstructorWithStatus() {
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
    public void testAttendanceConstructorWithoutStatus() {
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
    public void testSetAttendanceDay() {
        Attendance attendance = new Attendance();
        LocalDate date = LocalDate.of(2024, 7, 8); // A Monday

        attendance.setAttendanceDay(date);

        assertEquals(AttendanceDay.MON, attendance.getAttendanceDay());
    }

    @Test
    public void testIsCancelableOnTime() {
        LocalDate date = LocalDate.now().plusDays(2);
        Attendance attendance = new Attendance(date, DayType.FULL_DAY, mockKid);

        assertTrue(attendance.isCancelableOnTime());
    }

    @Test
    public void testIsCancelable() {
        LocalDate date = LocalDate.now().plusDays(1);
        Attendance attendance = new Attendance(date, DayType.FULL_DAY, mockKid);

        assertTrue(attendance.isCancelable());
    }

    @Test
    public void testIsAlreadyCanceled() {
        Attendance attendance = new Attendance();
        attendance.setAttendanceStatus(AttendanceStatus.CANCELED_ON_TIME);

        assertTrue(attendance.isAlreadyCanceled());
    }

    @Test
    public void testSetAndGetKid() {
        Attendance attendance = new Attendance();
        attendance.setKid(mockKid);

        assertEquals(mockKid, attendance.getKid());
    }

    @Test
    public void testSetAndGetAttendanceStatus() {
        Attendance attendance = new Attendance();
        attendance.setAttendanceStatus(AttendanceStatus.ABSENT);

        assertEquals(AttendanceStatus.ABSENT, attendance.getAttendanceStatus());
    }

    @Test
    public void testSetAndGetAttendanceDay() {
        Attendance attendance = new Attendance();
        LocalDate date = LocalDate.of(2024, 7, 8); // A Monday

        attendance.setAttendanceDay(date);

        assertEquals(AttendanceDay.MON, attendance.getAttendanceDay());
    }

    @Test
    public void testIsAndSetExcused() {
        Attendance attendance = new Attendance();
        attendance.setExcused(true);

        assertTrue(attendance.isExcused());
    }

    @Test
    public void testSetAttendanceDayFromDate() {
        Attendance attendance = new Attendance();
        LocalDate date = LocalDate.of(2024, 7, 8); // A Monday

        attendance.setAttendanceDay(date);

        DayOfWeek dayOfWeek = date.getDayOfWeek();
        AttendanceDay expectedAttendanceDay = AttendanceDay.valueOf(dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.ENGLISH).toUpperCase());

        assertEquals(expectedAttendanceDay, attendance.getAttendanceDay());
    }

    @Test
    public void testIsCancelableOnTimeFalse() {
        LocalDate date = LocalDate.now().minusDays(1);
        Attendance attendance = new Attendance(date, DayType.FULL_DAY, mockKid);

        assertFalse(attendance.isCancelableOnTime());
    }

    @Test
    public void testIsCancelableFalse() {
        LocalDate date = LocalDate.now().minusDays(1);
        Attendance attendance = new Attendance(date, DayType.FULL_DAY, mockKid);

        assertFalse(attendance.isCancelable());
    }

    @Test
    public void testIsAlreadyCanceledFalse() {
        Attendance attendance = new Attendance();
        attendance.setAttendanceStatus(AttendanceStatus.ATTENDED);

        assertFalse(attendance.isAlreadyCanceled());
    }

}
