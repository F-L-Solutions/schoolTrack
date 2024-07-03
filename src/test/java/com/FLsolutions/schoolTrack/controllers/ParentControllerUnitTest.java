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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.FLsolutions.schoolTrack.dtos.ParentResponseDto;
import com.FLsolutions.schoolTrack.models.Parent;
import com.FLsolutions.schoolTrack.services.ParentService;

@ContextConfiguration
@WebMvcTest(ParentController.class) // Only this annotation for controller testing
class ParentControllerUnitTest {
    
    @MockBean 
    private ParentService parentService; // Mocking the service layer
    
    @Autowired 
    private MockMvc mockMvc; // For performing HTTP requests and assertions
    
    private List<ParentResponseDto> responseDtoList;
    
    @BeforeEach
    void setUp() {
        // Setting up mock data
        Parent parent1 = new Parent("Parent", "One", "parentone@example.com", "12345");
        Parent parent2 = new Parent("Parent", "Two", "parenttwo@example.com", "12345");
        parent1.setSysId(1L);
        parent2.setSysId(2L);
        
        ParentResponseDto responseDto1 = new ParentResponseDto(parent1);
        ParentResponseDto responseDto2 = new ParentResponseDto(parent2);
        
        responseDtoList = new ArrayList<ParentResponseDto>();
        responseDtoList.add(responseDto1);
        responseDtoList.add(responseDto2);
    }
    
    @Test
    void can_return_all_parents() throws Exception {
        // Mocking the service method
        Mockito.when(parentService.fetchAllParents()).thenReturn(responseDtoList);
        
        // Performing a GET request and asserting the response
        mockMvc
            .perform(MockMvcRequestBuilders.get("/parents"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].sysId", is(1)))
            .andExpect(jsonPath("$[0].firstName", is("Parent")))
            .andExpect(jsonPath("$[0].lastName", is("One")))
            .andExpect(jsonPath("$[0].sysId", is(2)))
            .andExpect(jsonPath("$[0].firstName", is("Parent")))
            .andExpect(jsonPath("$[0].lastName", is("Two")));
    }
}
