package com.FLsolutions.schoolTrack.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Primary
public class PasswordConfig {

    @Bean
    @Primary
    PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
