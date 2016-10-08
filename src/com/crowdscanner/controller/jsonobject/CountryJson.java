package com.crowdscanner.controller.jsonobject;

public class CountryJson {

	private String countryNameCode;
	
	private String countryName;

	private AdministrativeAreaJson administrativeArea;
	
	private SubAdministrativeAreaJson  subAdministrativeArea;
	
	public AdministrativeAreaJson getAdministrativeArea() {
		return administrativeArea;
	}

	public void setAdministrativeArea(AdministrativeAreaJson administrativeArea) {
		this.administrativeArea = administrativeArea;
	}	
	
	public SubAdministrativeAreaJson getSubAdministrativeArea() {
		return subAdministrativeArea;
	}

	public void setSubAdministrativeArea(
			SubAdministrativeAreaJson subAdministrativeArea) {
		this.subAdministrativeArea = subAdministrativeArea;
	}

	public String getCountryNameCode() {
		return countryNameCode;
	}

	public void setCountryNameCode(String countryNameCode) {
		this.countryNameCode = countryNameCode;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
}
