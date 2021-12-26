package com.hotelbooking.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustDetailResModel {
	private String roomno;
	private CustomerModel customer;
	private String description;
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getRoomno() {
		return roomno;
	}
	public void setRoomno(String roomno) {
		this.roomno = roomno;
	}
	public CustomerModel getCustomer() {
		return customer;
	}
	public void setCustomer(CustomerModel customer) {
		this.customer = customer;
	}
	@Override
	public String toString() {
		return "CustDetailResModel [roomno=" + roomno + ", customer=" + customer + ", description=" + description + "]";
	}
	
	
}
