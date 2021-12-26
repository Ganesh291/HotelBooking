package com.hotelbooking.model;

public class FacilitiesModel {

	private String airConditioner;
	private String wifi;
	private String doubleCot;
	public String getAirConditioner() {
		return airConditioner;
	}
	public void setAirConditioner(String airConditioner) {
		this.airConditioner = airConditioner;
	}
	public String getWifi() {
		return wifi;
	}
	public void setWifi(String wifi) {
		this.wifi = wifi;
	}
	public String getDoubleCot() {
		return doubleCot;
	}
	public void setDoubleCot(String doubleCot) {
		this.doubleCot = doubleCot;
	}
	@Override
	public String toString() {
		return "{airConditioner:" + airConditioner + ", wifi:" + wifi + ", doubleCot:" + doubleCot
				+ "}";
	}
	
}
