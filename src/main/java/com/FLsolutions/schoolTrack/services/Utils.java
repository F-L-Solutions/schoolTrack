package com.FLsolutions.schoolTrack.services;

import java.util.ArrayList;
import java.util.List;

import org.passay.CharacterData;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;

public class Utils {
	
	private Utils() {
		
	}
	
	public static String createUserName(String firstName, String lastName) {
		return firstName.concat(lastName);
	}
	
	public static String generatePassword() {
	    PasswordGenerator gen = new PasswordGenerator();
	    EnglishCharacterData lowerCaseChars = EnglishCharacterData.LowerCase;
	    CharacterRule lowerCaseRule = new CharacterRule(lowerCaseChars);
	    lowerCaseRule.setNumberOfCharacters(3);

	    EnglishCharacterData upperCaseChars = EnglishCharacterData.UpperCase;
	    CharacterRule upperCaseRule = new CharacterRule(upperCaseChars);
	    upperCaseRule.setNumberOfCharacters(3);

	    EnglishCharacterData digitChars = EnglishCharacterData.Digit;
	    CharacterRule digitRule = new CharacterRule(digitChars);
	    digitRule.setNumberOfCharacters(3);

	    CharacterRule specialCharacterRule = new CharacterRule(new CharacterData() {
	        @Override
	        public String getErrorCode() {
	            return "ERROR";
	        }

	        @Override
	        public String getCharacters() {
	            return "!@#%*()-_+=<>?";
	        }
	    });
	    
	    specialCharacterRule.setNumberOfCharacters(3);
	    
	    List<CharacterRule> ruleList = new ArrayList<CharacterRule>();
	    ruleList.add(lowerCaseRule);
	    ruleList.add(upperCaseRule);
	    ruleList.add(digitRule);
	    ruleList.add(specialCharacterRule);
	    
	    String password = gen.generatePassword(12, ruleList);
	    
	    return password;
	}
}
