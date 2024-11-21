package com.FLsolutions.schoolTrack.services;

import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.FLsolutions.schoolTrack.exceptions.DuplicateEmailException;
import com.FLsolutions.schoolTrack.exceptions.GenericUserException;
import com.FLsolutions.schoolTrack.models.User;
import com.FLsolutions.schoolTrack.repositories.UserRepository;

@Service
@Primary
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;
	public final PasswordEncoder passwordEncoder;

	public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
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
        String password = Utils.generatePassword();
        
		// !!! VISIBILITY OF PASSWORD IS TEMPORARY FOR TESTING, NEEDS TO BE REMOVED LATER IN DEVELOPMENT
        System.out.println("User password: " + password);
        
        String encodedPassword = passwordEncoder.encode(password);
        newUser.setPassword(encodedPassword);
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
