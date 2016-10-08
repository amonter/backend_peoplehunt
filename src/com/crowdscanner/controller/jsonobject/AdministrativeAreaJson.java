package com.crowdscanner.controller.jsonobject;

public class AdministrativeAreaJson {

	private String administrativeAreaName;
	
	private SubAdministrativeAreaJson subAdministrativeArea;	

	public SubAdministrativeAreaJson getSubAdministrativeArea() {
		return subAdministrativeArea;
	}

	public void setSubAdministrativeArea(
			SubAdministrativeAreaJson subAdministrativeArea) {
		this.subAdministrativeArea = subAdministrativeArea;
	}

	public String getAdministrativeAreaName() {
		return administrativeAreaName;
	}

	public void setAdministrativeAreaName(String administrativeAreaName) {
		this.administrativeAreaName = administrativeAreaName;
	}
}
