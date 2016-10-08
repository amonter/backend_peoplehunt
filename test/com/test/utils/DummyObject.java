package com.test.utils;

public class DummyObject {

	private String firstString;

	private String secondString;
	
	private Integer aNumber;

	public DummyObject(String a, String b, Integer c) {

		this.firstString = a;
		this.secondString = b;
		this.aNumber = c;

	}

	public Integer getANumber() {
		return aNumber;
	}

	public void setANumber(Integer number) {
		aNumber = number;
	}

	public String getFirstString() {
		return firstString;
	}

	public void setFirstString(String firstString) {
		this.firstString = firstString;
	}

	public String getSecondString() {
		return secondString;
	}

	public void setSecondString(String secondString) {
		this.secondString = secondString;
	}



}
