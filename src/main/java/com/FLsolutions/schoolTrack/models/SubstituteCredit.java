package com.FLsolutions.schoolTrack.models;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "substitute_credits")
public class SubstituteCredit {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private long sysId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "kid_id")
	private Kid kid;

	@Column(name = "expiration_date")
	private LocalDateTime expirationDate;

	private boolean used;

	@CreationTimestamp
	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	public SubstituteCredit() {

	}

	public SubstituteCredit(Kid kid) {
		this.kid = kid;
		this.expirationDate = this.setExpirationDate();
		this.used = false;
	}

	public long getSysId() {
		return sysId;
	}

	public Kid getKid() {
		return kid;
	}

	public void setKid(Kid kid) {
		this.kid = kid;
	}

	public LocalDateTime getExpirationDate() {
		return expirationDate;
	}

	public LocalDateTime setExpirationDate() {
		LocalDateTime defaultExpirationDate = LocalDateTime.now().plusWeeks(1);
		return defaultExpirationDate;
	}

	public boolean isUsed() {
		return used;
	}

	public void setUsed(boolean used) {
		this.used = used;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setSysId(long sysId) {
		this.sysId = sysId;
	}

	public void setExpirationDate(LocalDateTime expirationDate) {
		this.expirationDate = expirationDate;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

}
