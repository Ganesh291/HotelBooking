package com.hotelbooking.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@Table(name="Hotelowner")
public class HotelOwnerEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@Column
	private String roomNo;
	private String status;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Integer customerId;
	private String airConditioner;
	private String wifi;
	private String doubleCot;
	private Integer price;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
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
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public String getRoomNo() {
		return roomNo;
	}
	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "HotelOwnerModel [id=" + id + ", roomNo=" + roomNo + ", status=" + status + ", customerId=" + customerId
				+ ", airConditioner=" + airConditioner + ", wifi=" + wifi + ", doubleCot=" + doubleCot + ", price=" + price + "]";
	}
	
	
}
