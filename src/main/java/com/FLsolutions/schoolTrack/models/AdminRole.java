package com.FLsolutions.schoolTrack.models;

public enum AdminRole {
	ADMIN("admin"), SUPER_ADMIN("super admin");
	
	private String name;

	AdminRole(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	

}
