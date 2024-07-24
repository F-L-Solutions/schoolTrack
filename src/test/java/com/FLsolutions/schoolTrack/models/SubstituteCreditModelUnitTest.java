package com.FLsolutions.schoolTrack.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SubstituteCreditModelUnitTest {

	private SubstituteCredit credit1;
	private SubstituteCredit credit2;
	private Kid kid;
	private LocalDate expirationDate;
	private boolean used;
	private LocalDateTime createdAt;

	@BeforeEach
	void setUp() {
		kid = new Kid("Test", "Kid", null);
		expirationDate = LocalDate.now().plusWeeks(1);
		used = true;
		createdAt = LocalDateTime.now();

		credit1 = new SubstituteCredit(kid);
		credit1.setSysId(1L);
		credit1.setCreatedAt(createdAt);

		credit2 = new SubstituteCredit();
		credit2.setSysId(2L);
		credit2.setKid(kid);
		credit2.setExpirationDate();
		credit2.setUsed(used);
		credit2.setCreatedAt(createdAt);
	}

	@Test
	void test_withValidArguments_allFieldsAreSetCorrectly() {
		assertEquals(1L, credit1.getSysId());
		assertEquals(kid, credit1.getKid());
		assertEquals(false, credit1.isUsed());
		assertEquals(createdAt, credit1.getCreatedAt());
	}

	@Test
	void testDefaultContrustor_allFieldsAreSetCorrectly() {
		assertEquals(2L, credit2.getSysId());
		assertEquals(kid, credit2.getKid());
		assertEquals(expirationDate, credit2.getExpirationDate());
		assertEquals(used, credit2.isUsed());
		assertEquals(createdAt, credit2.getCreatedAt());
	}

	@Test
	void testGettersAndSetters_runsCorrectly() {
		SubstituteCredit credit = new SubstituteCredit();

		credit.setSysId(3L);
		assertEquals(3L, credit.getSysId());

		credit.setKid(kid);
		assertEquals(kid, credit.getKid());

		credit.setExpirationDate();
		assertEquals(expirationDate, credit.getExpirationDate());

		credit.setUsed(used);
		assertEquals(used, credit.isUsed());

		credit.setCreatedAt(createdAt);
		assertEquals(createdAt, credit.getCreatedAt());
	}
}
