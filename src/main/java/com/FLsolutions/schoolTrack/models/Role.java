package com.FLsolutions.schoolTrack.models;

public enum Role {
	ROLE_USER("user"), 
	ROLE_ADMIN("admin"), 
	ROLE_SUPER_ADMIN("super admin");
	
	private String name;

	Role(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
