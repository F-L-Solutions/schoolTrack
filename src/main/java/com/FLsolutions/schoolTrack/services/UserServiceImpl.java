package com.FLsolutions.schoolTrack.services;

import java.time.LocalDateTime;

import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.FLsolutions.schoolTrack.exceptions.DuplicateEmailException;
import com.FLsolutions.schoolTrack.exceptions.GenericUserException;
import com.FLsolutions.schoolTrack.models.User;
import com.FLsolutions.schoolTrack.repositories.UserRepository;

@Service
@Primary
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;

	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetailsService userDetailsService() {
		return new UserDetailsService() {
			@Override
			public UserDetails loadUserByUsername(String username) {
				return userRepository.findByUsername(username)
						.orElseThrow(() -> new GenericUserException("User not found", HttpStatus.NOT_FOUND));
			}
		};
	}

	@Override
	public User save(User newUser) {
        validateUniqueEmail(newUser.getEmail());
        validateUniqueUsername(newUser.getUsername());

        return userRepository.save(newUser);
	}
	
	@Override
	public void validateUniqueEmail(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new DuplicateEmailException("Email already exists", HttpStatus.CONFLICT);
        }
    }
    
	@Override
	public void validateUniqueUsername(String username) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new GenericUserException("Username already exists", HttpStatus.CONFLICT);
        }
    }

}
