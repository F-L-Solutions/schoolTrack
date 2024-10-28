package com.FLsolutions.schoolTrack.services;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.FLsolutions.schoolTrack.models.User;

public interface UserService {

	public UserDetailsService userDetailsService();
	
	public User save(User newUser);
}