package com.crowdscanner.model;

public enum MatchCategory {
	
	CLONE(5),SUPERMATCH(4),MEDIOMATCH(3),LESSMATCH(2),THEREST(1),NOBODY(0);
	
	private final 	int number;
	
	private MatchCategory(int number) {
		this.number = number;
	}
	
	public int retriveMatchNumber() {		
		return number;
	}
	
}
