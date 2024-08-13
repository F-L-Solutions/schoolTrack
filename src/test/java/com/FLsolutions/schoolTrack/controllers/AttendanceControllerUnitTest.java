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

import com.FLsolutions.schoolTrack.dtos.AttendanceCreationRequestDto;
import com.FLsolutions.schoolTrack.dtos.AttendanceResponseDto;
import com.FLsolutions.schoolTrack.dtos.StatusResponseDto;
import com.FLsolutions.schoolTrack.models.Attendance;
import com.FLsolutions.schoolTrack.models.AttendanceDay;
import com.FLsolutions.schoolTrack.models.AttendanceStatus;
import com.FLsolutions.schoolTrack.models.DayType;
import com.FLsolutions.schoolTrack.models.Kid;
import com.FLsolutions.schoolTrack.services.AttendanceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@ContextConfiguration
@WebMvcTest(AttendanceController.class)
public class AttendanceControllerUnitTest {

	@MockBean
	private AttendanceService attendanceService;

	@Autowired
	private MockMvc mockMvc;

	private ObjectMapper objectMapper;

	private AttendanceResponseDto attendanceResponseDto1;
	private AttendanceResponseDto attendanceResponseDto2;
	private List<AttendanceResponseDto> responseDtoList;
	private AttendanceCreationRequestDto bulkRequestDto;
	private AttendanceCreationRequestDto singleRequestDto;

	private Kid mockKid;

	@BeforeEach
	void setup() {
		// Initialise ObjectMapper instance
		objectMapper = new ObjectMapper();
		// Register the JavaTimeModule to handle Java 8 date/time types
		objectMapper.registerModule(new JavaTimeModule());

		mockKid = new Kid();
		mockKid.setSysId(3L);

		Attendance attendance1 = new Attendance(LocalDate.of(2024, 8, 13), DayType.FULL_DAY, mockKid,
				AttendanceStatus.ATTENDED);
		Attendance attendance2 = new Attendance(LocalDate.of(2024, 8, 14), DayType.FULL_DAY, mockKid);

		attendance1.setSysId(1L);
		attendance2.setSysId(2L);

		attendanceResponseDto1 = new AttendanceResponseDto(attendance1);
		attendanceResponseDto2 = new AttendanceResponseDto(attendance2);

		responseDtoList = new ArrayList<AttendanceResponseDto>();
		responseDtoList.add(attendanceResponseDto1);
		responseDtoList.add(attendanceResponseDto2);

		List<AttendanceDay> attendanceDays = new ArrayList<AttendanceDay>();
		attendanceDays.add(AttendanceDay.MON);
		attendanceDays.add(AttendanceDay.TUE);
		attendanceDays.add(AttendanceDay.WED);
		attendanceDays.add(AttendanceDay.THU);
		attendanceDays.add(AttendanceDay.FRI);

		bulkRequestDto = new AttendanceCreationRequestDto(LocalDate.of(2024, 8, 12), LocalDate.of(2024, 8, 16),
				DayType.FULL_DAY, mockKid.getUserName(), attendanceDays);
		singleRequestDto = new AttendanceCreationRequestDto(LocalDate.of(2024, 8, 12), DayType.HALF_DAY,
				mockKid.getUserName());
	}

	@Test
	void POST_create_returnsOkStatus() throws Exception {
		StatusResponseDto responseDto = new StatusResponseDto("ok");

		Mockito.when(attendanceService.createAttendance(Mockito.any())).thenReturn(responseDto);

		mockMvc.perform(MockMvcRequestBuilders.post("/attendances")
				.content(objectMapper.writeValueAsString(singleRequestDto))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	void POST_bulkCreate_returnsOkStatus() throws Exception {
		StatusResponseDto responseDto = new StatusResponseDto("ok");

		Mockito.when(attendanceService.bulkCreateAttendances(Mockito.any())).thenReturn(responseDto);

		mockMvc.perform(MockMvcRequestBuilders.post("/attendances/bulk-create")
				.content(objectMapper.writeValueAsString(bulkRequestDto))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	void POST_cancel_returnsOkStatus() throws Exception {
		StatusResponseDto responseDto = new StatusResponseDto("ok");

		Mockito.when(attendanceService.cancelAttendance(Mockito.any())).thenReturn(responseDto);

		mockMvc.perform(MockMvcRequestBuilders.post("/attendances/1/cancel"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.status", is("ok")));
	}

	@Test
	void GET_getAllAttendances_returnsAttendanceList() throws Exception {
		Mockito.when(attendanceService.fetchAllAttendances()).thenReturn(responseDtoList);

		mockMvc.perform(MockMvcRequestBuilders.get("/attendances"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.[0].sysId", is(1)))
				.andExpect(jsonPath("$.[0].date", is("2024-08-13")))
				.andExpect(jsonPath("$.[0].dayType", is("FULL_DAY")))
				.andExpect(jsonPath("$.[0].kid.sysId", is(3)))
				.andExpect(jsonPath("$.[0].attendanceStatus", is("ATTENDED")))
				.andExpect(jsonPath("$.[0].attendanceDay", is("TUE")))
				.andExpect(jsonPath("$.[0].excused", is(false)))
				.andExpect(jsonPath("$.[1].sysId", is(2)))
				.andExpect(jsonPath("$.[1].date", is("2024-08-14")))
				.andExpect(jsonPath("$.[1].dayType", is("FULL_DAY")))
				.andExpect(jsonPath("$.[1].kid.sysId", is(3)))
				.andExpect(jsonPath("$.[1].attendanceStatus", is("IDLE")))
				.andExpect(jsonPath("$.[1].attendanceDay", is("WED")))
				.andExpect(jsonPath("$.[1].excused", is(false)));
	}

	@Test
	void GET_getAttendancesByKidSysId_returnsAttendanceList() throws Exception {
		Mockito.when(attendanceService.fetchAttendanceByKidSysId(3L)).thenReturn(responseDtoList);

		mockMvc.perform(MockMvcRequestBuilders.get("/attendances/kid/3"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.[0].sysId", is(1)))
				.andExpect(jsonPath("$.[0].date", is("2024-08-13")))
				.andExpect(jsonPath("$.[0].dayType", is("FULL_DAY")))
				.andExpect(jsonPath("$.[0].kid.sysId", is(3)))
				.andExpect(jsonPath("$.[0].attendanceStatus", is("ATTENDED")))
				.andExpect(jsonPath("$.[0].attendanceDay", is("TUE")))
				.andExpect(jsonPath("$.[0].excused", is(false)))
				.andExpect(jsonPath("$.[1].sysId", is(2)))
				.andExpect(jsonPath("$.[1].date", is("2024-08-14")))
				.andExpect(jsonPath("$.[1].dayType", is("FULL_DAY")))
				.andExpect(jsonPath("$.[1].kid.sysId", is(3)))
				.andExpect(jsonPath("$.[1].attendanceStatus", is("IDLE")))
				.andExpect(jsonPath("$.[1].attendanceDay", is("WED")))
				.andExpect(jsonPath("$.[1].excused", is(false)));
	}

	@Test
	void GET_getAttendanceBySysId_returnsAttendance() throws Exception {
		Mockito.when(attendanceService.fetchAttendanceBySysId(1L)).thenReturn(attendanceResponseDto1);

		mockMvc.perform(MockMvcRequestBuilders.get("/attendances/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.sysId", is(1)))
				.andExpect(jsonPath("$.date", is("2024-08-13")))
				.andExpect(jsonPath("$.dayType", is("FULL_DAY")))
				.andExpect(jsonPath("$.kid.sysId", is(3)))
				.andExpect(jsonPath("$.attendanceStatus", is("ATTENDED")))
				.andExpect(jsonPath("$.attendanceDay", is("TUE")))
				.andExpect(jsonPath("$.excused", is(false)));
	}
}
