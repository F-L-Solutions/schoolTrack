package com.FLsolutions.schoolTrack.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import com.FLsolutions.schoolTrack.dtos.AdminCreationRequestDto;
import com.FLsolutions.schoolTrack.dtos.AdminResponseDto;
import com.FLsolutions.schoolTrack.dtos.StatusResponseDto;
import com.FLsolutions.schoolTrack.models.Admin;
import com.FLsolutions.schoolTrack.models.AdminRole;
import com.FLsolutions.schoolTrack.models.User;
import com.FLsolutions.schoolTrack.services.AdminService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ContextConfiguration
@WebMvcTest(AdminController.class)
public class AdminControllerUnitTest {

	@MockBean
	private AdminService adminService;

	@Autowired
	private MockMvc mockMvc;

	private ObjectMapper objectMapper;

	private AdminCreationRequestDto creationRequestDto;
	private AdminResponseDto responseDto1;
	private AdminResponseDto responseDto2;
	private List<AdminResponseDto> responseDtoList;

	@BeforeEach
	void setup() {
		// Initialise ObjectMapper instance
		objectMapper= new ObjectMapper();

		Admin admin1 = new Admin("Test1", "Admin1", "test1@admin.com", AdminRole.ADMIN);
		Admin admin2 = new Admin("Test2", "Admin2", "test2@admin.com", AdminRole.SUPER_ADMIN);
		admin1.setSysId(1L);
		admin2.setSysId(2L);

		responseDto1 = new AdminResponseDto(admin1);
		responseDto2 = new AdminResponseDto(admin2);

		responseDtoList = new ArrayList<AdminResponseDto>();
		responseDtoList.add(responseDto1);
		responseDtoList.add(responseDto2);

		creationRequestDto = new AdminCreationRequestDto("Created", "Admin", AdminRole.ADMIN, "created@admin.com");
	}

	@Test
	void POST_create_returnsOkStatus() throws Exception {
		StatusResponseDto responseDto = new StatusResponseDto("ok");

		Mockito.when(adminService.createAdmin(Mockito.any())).thenReturn(responseDto);

		mockMvc.perform(MockMvcRequestBuilders.post("/admins")
				.content(objectMapper.writeValueAsString(creationRequestDto))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.status", is("ok")));
	}

	@Test
	void GET_getAllAdmins_returnsAdminList() throws Exception {
		Mockito.when(adminService.fetchAllAdmin()).thenReturn(responseDtoList);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/admins"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.[0].sysId", is(1)))
		.andExpect(jsonPath("$.[0].firstName", is("Test1")))
		.andExpect(jsonPath("$.[0].lastName", is("Admin1")))
		.andExpect(jsonPath("$.[0].adminRole", is("ADMIN")))
		.andExpect(jsonPath("$.[1].sysId", is(2)))
		.andExpect(jsonPath("$.[1].firstName", is("Test2")))
		.andExpect(jsonPath("$.[1].lastName", is("Admin2")))
		.andExpect(jsonPath("$.[1].adminRole", is("SUPER_ADMIN")));
	}

	@Test
	void GET_getAdminBySysId_returnsAdmin() throws Exception {
		Mockito.when(adminService.fetchAdminBySysId(1L)).thenReturn(responseDto1);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/admins/1"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.sysId", is(1)))
		.andExpect(jsonPath("$.firstName", is("Test1")))
		.andExpect(jsonPath("$.lastName", is("Admin1")))
		.andExpect(jsonPath("$.adminRole", is("ADMIN")));
	}

}
