package com.FLsolutions.schoolTrack.models;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.FLsolutions.schoolTrack.services.Utils;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long sysId;
	private String firstName;
	private String lastName;
	private String telNumber;
	@Column(name = "email", unique = true)
	private String email;

	@Column(name = "user_name", unique = true, nullable = false)
	private String userName;
	private String password;

	@CreationTimestamp
	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	public User() {
	}

	public User(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = Utils.createUserName(firstName, lastName);
	}

	public User(String firstName, String lastName, String email) {
		this(firstName, lastName);
		this.email = email;

		this.userName = Utils.createUserName(firstName, lastName);
		// method to create safe password to be done
		this.password = createPassword();
	}

	public User(String firstName, String lastName, String telNumber, String email) {
		this(firstName, lastName, email);
		this.telNumber = telNumber;
	}

//	public String createUserName(String firstName, String lastName) {
//		return firstName + lastName;
//	}

	public String createPassword() {
		return "password";
	}

	public Long getSysId() {
		return sysId;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getTelNumber() {
		return telNumber;
	}

	public String getEmail() {
		return email;
	}

	public String getUserName() {
		return userName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setTelNumber(String telNumber) {
		this.telNumber = telNumber;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setSysId(Long sysId) {
		this.sysId = sysId;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public String getPassword() {
		return password;
	}
}