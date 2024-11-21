package com.FLsolutions.schoolTrack.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.FLsolutions.schoolTrack.dtos.JwtAuthenticationResponseDto;
import com.FLsolutions.schoolTrack.dtos.SignInRequestDto;
import com.FLsolutions.schoolTrack.services.AuthenticationService;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
	
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
		super();
		this.authenticationService = authenticationService;
	}

    // Signup endpoint can be potentially used for creating superadmin, for now create endpoints in Parent and Admin controllers should suffice
	//@PostMapping("/signup")
    //public JwtAuthenticationResponseDto signup(@RequestBody SignUpRequestDto request) {
    //    return authenticationService.signup(request);
    //}

    @PostMapping("/signin")
    public JwtAuthenticationResponseDto signin(@RequestBody SignInRequestDto request) {
        return authenticationService.signin(request);
    }
}
