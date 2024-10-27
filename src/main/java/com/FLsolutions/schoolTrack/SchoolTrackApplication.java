package com.FLsolutions.schoolTrack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class SchoolTrackApplication {

	public static void main(String[] args) {
		SpringApplication.run(SchoolTrackApplication.class, args);
	}
	
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				//this probably needs to be changes in the future for safety
				registry.addMapping("/**").allowedOrigins("http://localhost:3000");
			}
		};
	}

}
