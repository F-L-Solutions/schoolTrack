package com.FLsolutions.schoolTrack.services;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.FLsolutions.schoolTrack.dtos.JwtAuthenticationResponseDto;
import com.FLsolutions.schoolTrack.dtos.SignInRequestDto;
import com.FLsolutions.schoolTrack.dtos.SignUpRequestDto;
import com.FLsolutions.schoolTrack.exceptions.GenericUserException;
import com.FLsolutions.schoolTrack.models.User;
import com.FLsolutions.schoolTrack.repositories.UserRepository;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

	private final UserRepository userRepository;
	private final UserService userService;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;

	public AuthenticationServiceImpl(UserRepository userRepository, UserService userService,
			PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
		super();
		this.userRepository = userRepository;
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
		this.jwtService = jwtService;
		this.authenticationManager = authenticationManager;
	}

	// Signup will not be generally used, admin will create new users. Signup can be
	// potentially later used for creating the 1st superadmin account.
	@Override
	public JwtAuthenticationResponseDto signup(SignUpRequestDto request) {

		User user = new User(request.getFirstName(), request.getLastName(), request.getEmail());
		//user = userService.save(user);

		String jwt = jwtService.generateToken(user);
		JwtAuthenticationResponseDto response = new JwtAuthenticationResponseDto();
		response.setToken(jwt);

		return response;
	}

	@Override
	public JwtAuthenticationResponseDto signin(SignInRequestDto request) {
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
		} catch (BadCredentialsException ex) {
			// Handle incorrect password scenario
			throw new GenericUserException("Cause: " + ex.getCause() + ", message: " + ex.getMessage() + ", stackTrace: " + ex.getStackTrace().toString() + ", localMessage: " + ex.getLocalizedMessage(), HttpStatus.UNAUTHORIZED);
		} catch (AuthenticationException ex) {
			// Handle other authentication errors
			throw new GenericUserException("Authentication failed. Please check your credentials.",
					HttpStatus.UNAUTHORIZED);
		}

		User user = userRepository.findByUsername(request.getUsername())
				.orElseThrow(() -> new IllegalArgumentException("Invalid username or password."));

		String jwt = jwtService.generateToken(user);

		JwtAuthenticationResponseDto response = new JwtAuthenticationResponseDto();
		response.setToken(jwt);

		return response;
	}
}
