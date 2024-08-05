package com.FLsolutions.schoolTrack.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.DayOfWeek;
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

import com.FLsolutions.schoolTrack.dtos.AttendanceCreationRequestDto;
import com.FLsolutions.schoolTrack.dtos.AttendanceResponseDto;
import com.FLsolutions.schoolTrack.dtos.StatusResponseDto;
import com.FLsolutions.schoolTrack.exceptions.GenericAttendanceException;
import com.FLsolutions.schoolTrack.models.Attendance;
import com.FLsolutions.schoolTrack.models.AttendanceDay;
import com.FLsolutions.schoolTrack.models.AttendanceStatus;
import com.FLsolutions.schoolTrack.models.DayType;
import com.FLsolutions.schoolTrack.models.Event;
import com.FLsolutions.schoolTrack.models.Kid;
import com.FLsolutions.schoolTrack.models.SubstituteCredit;
import com.FLsolutions.schoolTrack.repositories.AttendanceRepository;
import com.FLsolutions.schoolTrack.repositories.EventRepository;
import com.FLsolutions.schoolTrack.repositories.KidRepository;
import com.FLsolutions.schoolTrack.repositories.SubstituteCreditRepository;

@ExtendWith(MockitoExtension.class)
public class AttendanceServiceUnitTest {

	@Mock
	private AttendanceRepository attendanceRepository;

	@Mock
	private SubstituteCreditRepository creditRepository;

	@Mock
	private KidRepository kidRepository;

	@Mock
	private EventRepository eventRepository;

	@Mock
	private SubstituteCreditServiceImpl creditService;

	@InjectMocks
	private AttendanceServiceImpl attendanceService;

	private Attendance attendance1;
	private Attendance attendance2;
	private Attendance attendance3;
	private Attendance attendanceForCancel;
	private List<Attendance> attendanceList;
	private Kid kid;
	private Event event;

	private AttendanceCreationRequestDto bulkRequestDto;
	private AttendanceCreationRequestDto singleRequestDto;

	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);

		kid = new Kid();
		kid.setSysId(1L);
		kid.setUserName("TestKid");

		event = new Event();
		event.setAvailableSpots(1);

		attendance1 = new Attendance(LocalDate.of(2024, 8, 2), DayType.FULL_DAY, kid, AttendanceStatus.IDLE);
		attendance1.setSysId(2L);

		attendance2 = new Attendance(LocalDate.of(2024, 8, 1), DayType.HALF_DAY, kid,
				AttendanceStatus.CANCELED_ON_TIME);
		attendance2.setSysId(3L);

		attendance3 = new Attendance(LocalDate.of(2024, 7, 31), DayType.HALF_DAY, kid,
				AttendanceStatus.CANCELED_ON_TIME);
		attendance3.setSysId(4L);

		attendanceForCancel = new Attendance(LocalDate.of(2044, 12, 22), DayType.FULL_DAY, kid, AttendanceStatus.IDLE);

		attendanceList = new ArrayList<Attendance>();
		attendanceList.add(attendance2);
		attendanceList.add(attendance3);

		List<AttendanceDay> attendanceDays = new ArrayList<AttendanceDay>();
		attendanceDays.add(AttendanceDay.WED);
		attendanceDays.add(AttendanceDay.THU);

		bulkRequestDto = new AttendanceCreationRequestDto(LocalDate.of(2024, 7, 31), LocalDate.of(2024, 8, 1),
				DayType.HALF_DAY, kid.getUserName(), attendanceDays);
		singleRequestDto = new AttendanceCreationRequestDto(LocalDate.of(2024, 8, 2), DayType.FULL_DAY,
				kid.getUserName());
	}

	@Test
	void createAttendance_returnsStatusResponseDto() {
		when(kidRepository.findByUserName(anyString())).thenReturn(Optional.of(kid));
		when(attendanceRepository.findByKidIdAndDate(anyLong(), any())).thenReturn(Optional.empty());
		when(eventRepository.findByDate(any())).thenReturn(Optional.of(event));

		StatusResponseDto responseDto = attendanceService.createAttendance(singleRequestDto);

		verify(attendanceRepository, times(1)).save(any());
		assertNotNull(responseDto);
		assertThat(responseDto.getStatus()).isEqualTo("Attendance for TestKid was created.");
	}

	@Test
	void bulkCreateAttendances_returnsStatusResponseDto() {
		when(kidRepository.findByUserName(anyString())).thenReturn(Optional.of(kid));
		when(attendanceRepository.findByKidIdAndDate(anyLong(), any())).thenReturn(Optional.empty());
		when(eventRepository.findByDate(any())).thenReturn(Optional.of(event));

		StatusResponseDto responseDto = attendanceService.bulkCreateAttendances(bulkRequestDto);

		assertNotNull(responseDto);
		assertThat(responseDto.getStatus()).isEqualTo("2 attendances were created for TestKid");
		verify(attendanceRepository, times(1)).saveAll(anyList());
	}

	@Test
	void cancelAttendance_returnsStatusResponseDto_andCreatesCredit() {
		when(attendanceRepository.findById(anyLong())).thenReturn(Optional.of(attendanceForCancel));
		doNothing().when(creditService).createSubstituteCredit(any());

		StatusResponseDto responseDto = attendanceService.cancelAttendance(attendanceForCancel.getSysId());

		// Verify attendance was saved with the new status
		verify(attendanceRepository, times(1)).save(attendanceForCancel);
		// Verify substitute credit creation was called
		verify(creditService, times(1)).createSubstituteCredit(kid);
		// Check response status message
		assertNotNull(responseDto);
		assertThat(responseDto.getStatus())
				.isEqualTo("Attendance canceled and Substitute Credit granted successfully.");
	}

	@Test
	void fetchAllAttendances_returnsAttendanceList() {
		when(attendanceRepository.findAll()).thenReturn(attendanceList);

		List<AttendanceResponseDto> responseDtoList = attendanceService.fetchAllAttendances();

		assertNotNull(responseDtoList);
		assertThat(responseDtoList.get(0).getSysId()).isEqualTo(attendance2.getSysId());
		assertThat(responseDtoList.get(0).getDate()).isEqualTo(attendance2.getDate());
		assertThat(responseDtoList.get(0).getDayType()).isEqualTo(attendance2.getDayType());
		assertThat(responseDtoList.get(0).getAttendanceStatus()).isEqualTo(attendance2.getAttendanceStatus());
		assertThat(responseDtoList.get(1).getSysId()).isEqualTo(attendance3.getSysId());
		assertThat(responseDtoList.get(1).getDate()).isEqualTo(attendance3.getDate());
		assertThat(responseDtoList.get(1).getDayType()).isEqualTo(attendance3.getDayType());
		assertThat(responseDtoList.get(1).getAttendanceStatus()).isEqualTo(attendance3.getAttendanceStatus());
	}

	@Test
	void fetchAttendanceByKidSysId_returnsAttendance() {
		when(attendanceRepository.findByKidSysId(anyLong())).thenReturn(Optional.of(attendanceList));

		List<AttendanceResponseDto> responseDtoList = attendanceService.fetchAttendanceByKidSysId(kid.getSysId());

		assertNotNull(responseDtoList);
		assertThat(responseDtoList.get(0).getSysId()).isEqualTo(attendance2.getSysId());
		assertThat(responseDtoList.get(0).getDate()).isEqualTo(attendance2.getDate());
		assertThat(responseDtoList.get(0).getDayType()).isEqualTo(attendance2.getDayType());
		assertThat(responseDtoList.get(0).getKid().getSysId()).isEqualTo(attendance2.getKid().getSysId());
		assertThat(responseDtoList.get(0).getAttendanceStatus()).isEqualTo(attendance2.getAttendanceStatus());
		assertThat(responseDtoList.get(1).getSysId()).isEqualTo(attendance3.getSysId());
		assertThat(responseDtoList.get(1).getDate()).isEqualTo(attendance3.getDate());
		assertThat(responseDtoList.get(1).getDayType()).isEqualTo(attendance3.getDayType());
		assertThat(responseDtoList.get(1).getKid().getSysId()).isEqualTo(attendance3.getKid().getSysId());
		assertThat(responseDtoList.get(1).getAttendanceStatus()).isEqualTo(attendance3.getAttendanceStatus());
	}

	@Test
	void fetchAttendanceBySysId_returnsAttendance() {
		when(attendanceRepository.findById(anyLong())).thenReturn(Optional.of(attendance1));

		AttendanceResponseDto responseDto = attendanceService.fetchAttendanceBySysId(anyLong());

		assertNotNull(responseDto);
		assertThat(responseDto.getSysId()).isEqualTo(attendance1.getSysId());
		assertThat(responseDto.getDate()).isEqualTo(attendance1.getDate());
		assertThat(responseDto.getDayType()).isEqualTo(attendance1.getDayType());
		assertThat(responseDto.getKid().getSysId()).isEqualTo(attendance1.getKid().getSysId());
		assertThat(responseDto.getAttendanceStatus()).isEqualTo(attendance1.getAttendanceStatus());
	}

	@Test
	void cancelAttendance_whenAlreadyCanceled_throwsGenericAttendanceException() {
		when(attendanceRepository.findById(anyLong())).thenReturn(Optional.of(attendance3));

		GenericAttendanceException exception = assertThrows(GenericAttendanceException.class, () -> {
			attendanceService.cancelAttendance(attendance3.getSysId());
		});

		verify(creditService, times(0)).createSubstituteCredit(kid);
		assertNotNull(exception);
		assertEquals("Attendance is not possible to cancel anymore.", exception.getMessage());
	}
};