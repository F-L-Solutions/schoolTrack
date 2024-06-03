package com.FLsolutions.schoolTrack.services;

public class Utils {
	
	private Utils() {
		
	}
	
	public static String createUserName(String firstName, String lastName) {
		return firstName.concat(lastName);
	}

}
