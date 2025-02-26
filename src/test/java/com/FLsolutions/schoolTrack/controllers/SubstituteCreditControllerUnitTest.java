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
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.FLsolutions.schoolTrack.dtos.SubstituteCreditResponseDto;
import com.FLsolutions.schoolTrack.exceptions.GenericEventException;
import com.FLsolutions.schoolTrack.filters.JwtAuthenticationFilter;
import com.FLsolutions.schoolTrack.models.Kid;
import com.FLsolutions.schoolTrack.models.SubstituteCredit;
import com.FLsolutions.schoolTrack.services.SubstituteCreditService;

@ContextConfiguration
@WebMvcTest(SubstituteController.class)
public class SubstituteCreditControllerUnitTest {

	@MockBean
	private SubstituteCreditService creditService;
	
	@MockBean
	private JwtAuthenticationFilter jwtAuthenticationFilter;

	@Autowired
	private MockMvc mockMvc;

	private SubstituteCreditResponseDto responseDto1;
	private SubstituteCreditResponseDto responseDto2;
	private List<SubstituteCreditResponseDto> creditDtoList;

	private String expirationDate = LocalDate.now().plusWeeks(1).toString();
	private Kid mockKid;

	@BeforeEach
	void setUp() {

		mockKid = new Kid();
		mockKid.setSysId(3L);

		SubstituteCredit credit1 = new SubstituteCredit(mockKid);
		SubstituteCredit credit2 = new SubstituteCredit(mockKid);

		credit1.setSysId(1L);
		credit2.setSysId(2L);

		responseDto1 = new SubstituteCreditResponseDto(credit1);
		responseDto2 = new SubstituteCreditResponseDto(credit2);

		creditDtoList = new ArrayList<SubstituteCreditResponseDto>();
		creditDtoList.add(responseDto1);
		creditDtoList.add(responseDto2);
	}

	@Test
	void GET_getAllCredit_returnsCreditsList() throws Exception {
		Mockito.when(creditService.fetchAllSubstituteCredit()).thenReturn(creditDtoList);

		mockMvc.perform(MockMvcRequestBuilders.get("/substitutes/credit"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.[0].sysId", is(1)))
				.andExpect(jsonPath("$.[0].kid.sysId", is(3)))
				.andExpect(jsonPath("$.[0].expirationDate", is(expirationDate)))
				.andExpect(jsonPath("$.[0].used", is(false)))
				.andExpect(jsonPath("$.[1].sysId", is(2)))
				.andExpect(jsonPath("$.[1].kid.sysId", is(3)))
				.andExpect(jsonPath("$.[1].expirationDate", is(expirationDate)))
				.andExpect(jsonPath("$.[1].used", is(false)));
	}

	@Test
	void GET_getSubstituteCreditBySysId_returnsCredit() throws Exception {
		Mockito.when(creditService.fetchSubstituteCreditBySysId(1L)).thenReturn(responseDto1);

		mockMvc.perform(MockMvcRequestBuilders.get("/substitutes/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.sysId", is(1)))
				.andExpect(jsonPath("$.kid.sysId", is(3)))
				.andExpect(jsonPath("$.expirationDate", is(expirationDate)))
				.andExpect(jsonPath("$.used", is(false)));
	}

	@Test
	void GET_getAllCredit_whenNoCreditExists_returnsError() throws Exception {
		Mockito.when(creditService.fetchAllSubstituteCredit())
				.thenThrow(new GenericEventException("Validation Failed", HttpStatus.NOT_FOUND));

		mockMvc.perform(MockMvcRequestBuilders.get("/substitutes/credit"))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.message", is("Validation Failed")))
				.andExpect(jsonPath("$.status", is(404)));
	}

	@Test
	void GET_getSubstituteCreditBySysId__withInvalidId_returnsError() throws Exception {
		Mockito.when(creditService.fetchSubstituteCreditBySysId(999L))
				.thenThrow(new GenericEventException("Validation Failed", HttpStatus.NOT_FOUND));

		mockMvc.perform(MockMvcRequestBuilders.get("/substitutes/999"))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.message", is("Validation Failed")))
				.andExpect(jsonPath("$.status", is(404)));
	}
}
