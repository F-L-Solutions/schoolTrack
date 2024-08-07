package com.FLsolutions.schoolTrack.dtos;

import java.time.LocalDate;

import com.FLsolutions.schoolTrack.models.Kid;
import com.FLsolutions.schoolTrack.models.SubstituteCredit;

public class SubstituteCreditResponseDto implements ResponseDto {

	private final long sysId;
	private final Kid kid;
	private final LocalDate expirationDate;
	private final boolean used;

	public SubstituteCreditResponseDto(SubstituteCredit credit) {
		super();
		this.sysId = credit.getSysId();
		this.kid = credit.getKid();
		this.expirationDate = credit.getExpirationDate();
		this.used = credit.isUsed();
	}

	public long getSysId() {
		return sysId;
	}

	public KidResponseDto getKid() {
		KidResponseDto kidResponseDto = new KidResponseDto(kid);
		return kidResponseDto;
	}

	public LocalDate getExpirationDate() {
		return expirationDate;
	}

	public boolean isUsed() {
		return used;
	}

}
