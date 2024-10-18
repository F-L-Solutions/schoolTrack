package com.FLsolutions.schoolTrack.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import com.FLsolutions.schoolTrack.services.Utils;

public class UserModelUnitTest {

	private User user1;
	private User user2;
	private User user3;
	private String firstName;
	private String lastName;
	private String telNumber;
	private String email;
	private String userName;
	private String password;
	private MockedStatic<Utils> mockedUtils;

	@BeforeEach
	void setUp() {
		// Mock the static Utils class
		mockedUtils = mockStatic(Utils.class);

		firstName = "Test";
		lastName = "User";
		telNumber = "1234567890";
		email = "test.user@example.com";
		userName = Utils.createUserName(firstName, lastName);
		password = "password";

		user1 = new User(firstName, lastName);
		user1.setSysId(1L);
		user1.setPassword(password);

		user2 = new User(firstName, lastName, email);
		user2.setSysId(2L);
		user2.setPassword(password);

		user3 = new User(firstName, lastName, telNumber, email);
		user3.setSysId(3L);
		user3.setPassword(password);
	}

	@AfterEach
	void tearDown() {
		// Close the static mock after each test
		mockedUtils.close();
	}

	@Test
	void testCreateUserName_staticMethodIsMockedCorrectly() {
		// Define what the mock should return
		when(Utils.createUserName("Test", "User")).thenReturn("TestUserMocked");

		// Create a new user which will use the mocked static method
		User user = new User("Test", "User");

		// Verify that the userName is as expected
		assertEquals("TestUserMocked", user.getUsername());
	}

	@Test
	void testConstructor_withBasicDetails_allFieldsAreSetCorrectly() {
		assertEquals(firstName, user1.getFirstName());
		assertEquals(lastName, user1.getLastName());
		assertEquals(userName, user1.getUsername());
		assertEquals(1L, user1.getSysId());
	}

	@Test
	void testConstructor_withEmail_allFieldsAreSetCorrectly() {
		assertEquals(firstName, user2.getFirstName());
		assertEquals(lastName, user2.getLastName());
		assertEquals(email, user2.getEmail());
		assertEquals(userName, user2.getUsername());
		assertEquals(2L, user2.getSysId());
	}

	@Test
	void testConstructor_withTelNumberAndEmail_allFieldsAreSetCorrectly() {
		assertEquals(firstName, user3.getFirstName());
		assertEquals(lastName, user3.getLastName());
		assertEquals(telNumber, user3.getTelNumber());
		assertEquals(email, user3.getEmail());
		assertEquals(userName, user3.getUsername());
		assertEquals(3L, user3.getSysId());
	}

	@Test
	void testGettersAndSetters_runsCorrectly() {
		User user = new User();

		user.setFirstName(firstName);
		assertEquals(firstName, user.getFirstName());

		user.setLastName(lastName);
		assertEquals(lastName, user.getLastName());

		user.setTelNumber(telNumber);
		assertEquals(telNumber, user.getTelNumber());

		user.setEmail(email);
		assertEquals(email, user.getEmail());

		user.setUserName(userName);
		assertEquals(userName, user.getUsername());

		user.setPassword(password);
		assertEquals(password, user.getPassword());

		user.setSysId(4L);
		assertEquals(4L, user.getSysId());
	}
}
