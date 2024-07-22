package com.FLsolutions.schoolTrack.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

import com.FLsolutions.schoolTrack.dtos.SubstituteCreditResponseDto;
import com.FLsolutions.schoolTrack.models.Kid;
import com.FLsolutions.schoolTrack.models.SubstituteCredit;
import com.FLsolutions.schoolTrack.repositories.SubstituteCreditRepository;

@ExtendWith(MockitoExtension.class)
public class SubstituteCreditUnitTest {

	@Mock
	private SubstituteCreditRepository creditRepository;

	@InjectMocks
	private SubstituteCreditServiceImpl creditService;

	private SubstituteCredit credit1;
	private SubstituteCredit credit2;
	private List<SubstituteCredit> creditList;

	private Kid mockKid;

	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);

		mockKid = new Kid();
		mockKid.setUserName("TestKid");
		mockKid.setSysId(3L);

		credit1 = new SubstituteCredit(mockKid);
		credit1.setSysId(1L);
		credit2 = new SubstituteCredit(mockKid);
		credit2.setSysId(2L);

		creditList = new ArrayList<>();
		creditList.add(credit1);
		creditList.add(credit2);

	}

	@Test
	void createSubstituteCredit_createsCredit() {
		creditService.createSubstituteCredit(mockKid);
		verify(creditRepository, times(1)).save(any(SubstituteCredit.class));
	}

	@Test
	void fetchAllSubstituteCredit_returnsCreditList() {
		when(creditRepository.findAll()).thenReturn(creditList);

		List<SubstituteCreditResponseDto> responseDto = creditService.fetchAllSubstituteCredit();

		assertNotNull(responseDto);
		assertThat(responseDto.get(0).getExpirationDate()).isEqualTo(credit1.getExpirationDate());
		assertThat(responseDto.get(0).getKid().getSysId()).isEqualTo(credit1.getKid().getSysId());
		assertThat(responseDto.get(0).getSysId()).isEqualTo(credit1.getSysId());
		assertThat(responseDto.get(1).getExpirationDate()).isEqualTo(credit2.getExpirationDate());
		assertThat(responseDto.get(1).getKid().getSysId()).isEqualTo(credit2.getKid().getSysId());
		assertThat(responseDto.get(1).getSysId()).isEqualTo(credit2.getSysId());

		verify(creditRepository, times(1)).findAll();
	}

	@Test
	void fetchSubstituteCreditBySysId_returnsCredit() {
		when(creditRepository.findBySysId(anyLong())).thenReturn(Optional.of(credit1));

		SubstituteCreditResponseDto responseDto = creditService.fetchSubstituteCreditBySysId(1L);
		assertNotNull(responseDto);
		assertThat(responseDto.getExpirationDate()).isEqualTo(responseDto.getExpirationDate());
		assertThat(responseDto.getKid().getSysId()).isEqualTo(responseDto.getKid().getSysId());
		assertThat(responseDto.getSysId()).isEqualTo(responseDto.getSysId());

		verify(creditRepository, times(1)).findBySysId(anyLong());
	}

}
