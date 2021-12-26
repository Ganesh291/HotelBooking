package com.hotelbooking.service;


import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hotelbooking.entity.CustomerEntity;
import com.hotelbooking.entity.HotelOwnerEntity;
import com.hotelbooking.model.CustDetailResModel;
import com.hotelbooking.model.CustomerModel;
import com.hotelbooking.model.HotelOwnerModel;
import com.hotelbooking.repository.CustomerRepository;
import com.hotelbooking.repository.HotelOwnerRepository;
import com.hotelbooking.utility.HotelBookingConstants;

@Service
@Transactional
public class HotelOwnerService {

	private static final Logger LOGGER=LoggerFactory.getLogger(HotelOwnerService.class);
	
	@Autowired
	private HotelOwnerRepository hotelOwnerRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	//returns the list of available rooms
	public List<HotelOwnerEntity> getAvailableRooms(){
		LOGGER.info("Inside getAvailableRooms : ");
		return hotelOwnerRepository.findByStatus(HotelBookingConstants.AVAILABLE);
	}
	
	//returns the customer details for each room
	public CustDetailResModel getCustomerDetailsforRoom(String roomNo){
		CustDetailResModel custDetail=new CustDetailResModel();
		LOGGER.info("Entering getCustomerDetailsforRoom with room no :{} ",roomNo);
		HotelOwnerEntity hotelEnty=hotelOwnerRepository.findByRoomNo(roomNo);
		if(hotelEnty.getCustomerId()!=null) {
			custDetail.setRoomno(roomNo);
			CustomerEntity custEnty=customerRepository.findByCustomerId(hotelEnty.getCustomerId());
			if(custEnty!=null) {
				CustomerModel custModel=new CustomerModel();
				custModel.setCustomerName(custEnty.getCustomerName());
				custModel.setAge(custEnty.getAge());
				custModel.setCity(custEnty.getCity());
				custModel.setCurrentStatus(custEnty.getCurrentStatus());
				custModel.setRoomNo(custEnty.getRoomNo());
				custDetail.setCustomer(custModel);
				custDetail.setDescription(null);
			}
		}else {
			custDetail.setRoomno(roomNo);
			custDetail.setCustomer(null);
			custDetail.setDescription(HotelBookingConstants.ROOM_IS_AVAILABLE);
		}
		LOGGER.info("Exiting getCustomerDetailsforRoom : ");
		return custDetail;
	}
	
	//returns all customer stayed in hotel
	public List<CustomerEntity> getCustomersStayed(){
		LOGGER.info("Entering getCustomersStayed : ");
		return customerRepository.findByCurrentStatus(HotelBookingConstants.OUT);
	}
	
	// method to add new rooms
	public String newRoomAdd(List<HotelOwnerModel> hotelModel) {
		LOGGER.info("Entering to insert new room record: ");
			HotelOwnerEntity hoEn=new HotelOwnerEntity();
		hotelModel.forEach(a-> {
			hoEn.setRoomNo(a.getRoomNo());
			hoEn.setStatus(a.getStatus());
			hoEn.setAirConditioner(a.getAirConditioner());
			hoEn.setPrice(a.getPrice());
			hoEn.setDoubleCot(a.getDoubleCot());
			hoEn.setWifi(a.getWifi());
			hotelOwnerRepository.saveAndFlush(hoEn);
		});
		LOGGER.info("Exiting newRoomAdd");
		return HotelBookingConstants.SUCCESS;
	}
	
	//to fetch all customer
	public List<CustomerEntity> getAllCustomers(){
		LOGGER.info("Fetching all the customer details");
		return customerRepository.findAll();
	}
	
	
}
