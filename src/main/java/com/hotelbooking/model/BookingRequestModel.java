package com.hotelbooking.model;

public class BookingRequestModel {

	private CustomerModel customer;
	private FacilitiesModel facilities;
	public CustomerModel getCustomer() {
		return customer;
	}
	public void setCustomer(CustomerModel customer) {
		this.customer = customer;
	}
	public FacilitiesModel getFacilities() {
		return facilities;
	}
	public void setFacilities(FacilitiesModel facilities) {
		this.facilities = facilities;
	}
	@Override
	public String toString() {
		return "BookingRequestModel [customer=" + customer + ", facilities=" + facilities + "]";
	}
	
}
