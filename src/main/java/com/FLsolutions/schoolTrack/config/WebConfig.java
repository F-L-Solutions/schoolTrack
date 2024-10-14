package com.FLsolutions.schoolTrack.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
public class WebConfig {
	
	@Value("${cors.allowed.origin}")
    private String allowedOrigin;
	
	@Bean
	FilterRegistrationBean<CorsFilter> corsFilter() {
		
		UrlBasedCorsConfigurationSource source= new UrlBasedCorsConfigurationSource();
		CorsConfiguration config= new CorsConfiguration();
		
		// Allows authentication information to be sent in CORS requests
		config.setAllowCredentials(true);
		
		//Restricts allowed requests to specific origin
		config.addAllowedOrigin(allowedOrigin);
		
		// Specify the allowed headers for incoming requests
		config.setAllowedHeaders(Arrays.asList(
				HttpHeaders.AUTHORIZATION,
				HttpHeaders.CONTENT_TYPE,
				HttpHeaders.ACCEPT
				));
		
		// Specify allowed HTTP methods for the CORS requests
		config.setAllowedMethods(Arrays.asList(
				HttpMethod.GET.name(),
				HttpMethod.POST.name(),
				HttpMethod.PUT.name(),
				HttpMethod.DELETE.name()
				));
		
		// Set the maximum age for the CORS preflight request to be cached (3600 seconds = 60 minutes)
		config.setMaxAge(3600L);
		
		// Register the configuration for all URLs (`/**`)
		source.registerCorsConfiguration("/**", config);
		
		// Create a filter registration bean to register the CorsFilter and ensure it has the correct order in the filter chain
		FilterRegistrationBean<CorsFilter> bean= new FilterRegistrationBean<CorsFilter>(new CorsFilter(source));
		
		// Set the order of the filter to ensure that it is applied early in the chain
		bean.setOrder(-102);
		return bean;
	}
}
