package com.FLsolutions.schoolTrack.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import com.FLsolutions.schoolTrack.SchoolTrackApplication;
import com.FLsolutions.schoolTrack.dtos.ParentResponseDto;
import com.FLsolutions.schoolTrack.models.Parent;
import com.FLsolutions.schoolTrack.repositories.ParentRepository;
import com.FLsolutions.schoolTrack.services.ParentService;

@SpringBootTest(classes = SchoolTrackApplication.class)
@TestPropertySource(properties = {"JWT_SECRET_KEY=your_test_jwt_secret_key"})
public class ParentServiceUnitTest {

	@MockBean
	private ParentRepository parentRepository;
	
	@MockBean
	private JwtService jwtService;
	
    @MockBean
    private KidService kidService;
	
	@Autowired
	private ParentService parentService;

//    @BeforeEach
//    void setUp() {
//        this.parentRepository = Mockito.mock(ParentRepository.class);
//        this.parentService = new ParentService(parentRepository); // Inject mocked repository
//    }

	@Test
	void getAllParents_returnsListOfParents() {
		// Mock data
		Parent parent1 = new Parent("Parent", "One", "parentone@example.com", "12345");
		Parent parent2 = new Parent("Parent", "Two", "parenttwo@example.com", "12345");
		parent1.setSysId(1L);
		parent2.setSysId(2L);

		List<Parent> parentList = new ArrayList<>();
		parentList.add(parent1);
		parentList.add(parent2);

		// Mock behavior
		Mockito.when(parentRepository.findAll()).thenReturn(parentList);

		// Test
		List<ParentResponseDto> response = parentService.fetchAllParents();

		// Assert
		assertNotNull(response);
		assertEquals(1, response.get(0).getSysId());
		assertEquals(2, response.get(1).getSysId());
	}
}
