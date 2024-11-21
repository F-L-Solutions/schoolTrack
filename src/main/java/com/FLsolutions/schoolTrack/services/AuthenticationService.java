package com.FLsolutions.schoolTrack.services;

import com.FLsolutions.schoolTrack.dtos.JwtAuthenticationResponseDto;
import com.FLsolutions.schoolTrack.dtos.SignInRequestDto;
import com.FLsolutions.schoolTrack.dtos.SignUpRequestDto;

public interface AuthenticationService {

	JwtAuthenticationResponseDto signup(SignUpRequestDto request);

	JwtAuthenticationResponseDto signin(SignInRequestDto request);

}
