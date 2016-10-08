package com.peoplehuntv2.model;

public enum LocationRoles {
	
	MANAGER(2),MEMBER(1),NOBODY(0);
	
	private final int number;
	
	private LocationRoles(int number) {
		this.number = number;
	}
	
	public int retriveRoleNumber() {		
		return number;
	}
	
}