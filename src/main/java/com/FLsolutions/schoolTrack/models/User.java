package com.FLsolutions.schoolTrack.models;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.FLsolutions.schoolTrack.services.Utils;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class User implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long sysId;
	private String firstName;
	private String lastName;
	private String telNumber;
	@Column(name = "email", unique = true)
	private String email;

	@Column(name = "user_name", unique = true, nullable = false)
	private String username;
	private String password;

	@Enumerated(EnumType.STRING)
	private Role role;

	@CreationTimestamp
	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(role.name()));
	}

	public User() {
	}

	public User(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = Utils.createUserName(firstName, lastName);
		this.role = Role.ROLE_USER;
	}

	public User(String firstName, String lastName, String email) {
		this(firstName, lastName);
		this.email = email;

		this.username = Utils.createUserName(firstName, lastName);
		// method to create safe password to be done
		this.password = createPassword();
		this.role = Role.ROLE_USER;
	}

	public User(String firstName, String lastName, String telNumber, String email) {
		this(firstName, lastName, email);
		this.telNumber = telNumber;
		this.role = Role.ROLE_USER;
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

	public void setUsername(String username) {
		this.username = username;
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

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public String getUsername() {
		return username;
	}
}