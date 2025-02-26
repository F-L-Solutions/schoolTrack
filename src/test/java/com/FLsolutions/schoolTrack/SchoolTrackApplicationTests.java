package com.FLsolutions.schoolTrack;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {"JWT_SECRET_KEY=your_test_jwt_secret_key"})
class SchoolTrackApplicationTests {

	@Test
	void contextLoads() {
	}

}
