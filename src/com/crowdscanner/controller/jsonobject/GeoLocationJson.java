package com.crowdscanner.controller.jsonobject;

import java.util.List;

public class GeoLocationJson {
	
	private String name;
	
	private StatusJson status;
	
	private List<PlaceMarkJson> placemark;
	
	public StatusJson getStatus() {
		return status;
	}

	public void setStatus(StatusJson status) {
		this.status = status;
	}	

	public List<PlaceMarkJson> getPlacemark() {
		return placemark;
	}

	public void setPlacemark(List<PlaceMarkJson> placemark) {
		this.placemark = placemark;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
