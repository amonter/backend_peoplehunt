package com.crowdscanner.controller.jsonobject;

public class PlaceMarkJson {

	private String address;
	
	private String id;
	
	private AddressDetailsJson addressDetails;

	public AddressDetailsJson getAddressDetails() {
		return addressDetails;
	}

	public void setAddressDetails(AddressDetailsJson addressDetails) {
		this.addressDetails = addressDetails;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
