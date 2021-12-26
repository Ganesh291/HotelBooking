package com.hotelbooking.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hotelbooking.entity.CustomerEntity;
import com.hotelbooking.entity.HotelOwnerEntity;
import com.hotelbooking.enumeration.ErrorEnum;
import com.hotelbooking.exception.BadRequestException;
import com.hotelbooking.model.BookingRequestModel;
import com.hotelbooking.model.BookingResponseModel;
import com.hotelbooking.model.CustomerModel;
import com.hotelbooking.model.FacilitiesModel;
import com.hotelbooking.repository.CustomerRepository;
import com.hotelbooking.repository.HotelOwnerRepository;
import com.hotelbooking.utility.HotelBookingConstants;

@Service
@Transactional
public class CustomerService {
	
	private static final Logger LOGGER=LoggerFactory.getLogger(CustomerService.class);
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private HotelOwnerRepository hotelOwnerRepository;
	
	//gets rooms availability based on specific criteria
	public List<String> getRoomsBasedOnCriteria(FacilitiesModel facilities) throws BadRequestException{
		List<String> roomsList=new ArrayList<>();
		LOGGER.info("Entering getRoomsBasedOnCriteria with facilities :{} ",facilities.toString());
			List<HotelOwnerEntity> hotelEntity=facilitiesValueCheck(facilities);
			if(!hotelEntity.isEmpty()) {
				hotelEntity.forEach(a-> roomsList.add(a.getRoomNo()));
			}else {
				LOGGER.error("No rooms available for given specification");
				throw new BadRequestException(ErrorEnum.NO_ROOMS_WITH_SPEC, ErrorEnum.NO_ROOMS_WITH_SPEC.getDescription());
			}
		LOGGER.info("Exiting getRoomsBasedOnCriteria");
		return roomsList;
	}
	
	//gets rooms price based on specification
	public List<String> getRoomsPriceOnCriteria(FacilitiesModel facilities) throws BadRequestException{
		List<String> roomsPriceList=new ArrayList<>();
		LOGGER.info("Entering getRoomsPriceOnCriteria with facilities: {}",facilities.toString());
		List<HotelOwnerEntity> hotelEntity=facilitiesValueCheck(facilities);
		if(!hotelEntity.isEmpty()) {
				hotelEntity.forEach(a-> roomsPriceList.add(a.getRoomNo()+(" - Rs. ").concat(a.getPrice().toString().concat(HotelBookingConstants.PER_NIGHT))));
			}else {
				LOGGER.error("No rooms available for given specification to fetch Price");
				throw new BadRequestException(ErrorEnum.NO_ROOMS_WITH_SPEC, ErrorEnum.NO_ROOMS_WITH_SPEC.getDescription());
			}
		LOGGER.info("Exiting getRoomsPriceOnCriteria");
		return roomsPriceList;
	}
	
	//common method to get room details & room price based on facilities
	private List<HotelOwnerEntity> facilitiesValueCheck(FacilitiesModel facilities) throws BadRequestException{
		List<HotelOwnerEntity> hotelEntity=new ArrayList<>();
		LOGGER.info("Entering getRoomsBasedOnFacilities: ");
		List<String> flagCheck = Arrays.asList(HotelBookingConstants.FLAG_CHECK.split("\\|"));
		if(flagCheck.contains(facilities.getAirConditioner()) && flagCheck.contains(facilities.getWifi()) && flagCheck.contains(facilities.getDoubleCot())) {
			hotelEntity=hotelOwnerRepository.findByAirConditionerAndWifiAndDoubleCot(facilities.getAirConditioner().toUpperCase(), facilities.getWifi().toUpperCase(), facilities.getDoubleCot().toUpperCase());
		}else {
			LOGGER.error("Bad Request not a expected value");
			throw new BadRequestException(ErrorEnum.INVALID_FACILITY_VALUES, ErrorEnum.INVALID_FACILITY_VALUES.getDescription());
		}
		LOGGER.info("Exiting getRoomsBasedOnFacilities");
		return hotelEntity;
	}
	
	//handles room booking
	public BookingResponseModel bookRooms(BookingRequestModel bookingReq) throws BadRequestException {
		LOGGER.info("Entering bookRooms with booking request : {} ",bookingReq.toString());
		BookingResponseModel bookingRes=new BookingResponseModel();
		try{
		List<String> roomsList=getRoomsBasedOnCriteria(bookingReq.getFacilities());
		LOGGER.info("Availables RoomNo's for the given specification : {}",roomsList);
		if(!roomsList.isEmpty()) {
			for(String roomNo : roomsList) {
				HotelOwnerEntity roomDetail=hotelOwnerRepository.findByRoomNo(roomNo);
				if(roomDetail.getStatus().equalsIgnoreCase(HotelBookingConstants.AVAILABLE) && roomDetail.getCustomerId()==null) {
					bookingRes.setRoomNo(roomDetail.getRoomNo());
					CustomerEntity custEnty=new CustomerEntity();
					Integer customerId=getCutomerIfExists(bookingReq.getCustomer());
					if(customerId==0) {
						LOGGER.info("Customer is new, Persisting the details..");
						custEnty.setCustomerName(bookingReq.getCustomer().getCustomerName());
						custEnty.setAge(bookingReq.getCustomer().getAge());
						custEnty.setCity(bookingReq.getCustomer().getCity());
						custEnty.setState(bookingReq.getCustomer().getState());
						custEnty.setRoomNo(roomDetail.getRoomNo());
						custEnty.setCurrentStatus(HotelBookingConstants.IN);
						customerRepository.save(custEnty);
						updateRoomForBooking(custEnty.getCustomerId(), roomDetail, bookingRes);
						customerRepository.updateCustomerStatusAndRoomId(HotelBookingConstants.IN, roomDetail.getRoomNo(), custEnty.getCustomerId());
					}else {
						CustomerEntity customerExists=customerRepository.findByCustomerId(customerId);
						if(customerExists!=null && customerExists.getCurrentStatus().equalsIgnoreCase(HotelBookingConstants.OUT)) {
							LOGGER.info("Customer Already Exists, Updating the roomNo and status");
							customerRepository.updateCustomerStatusAndRoomId(HotelBookingConstants.IN,roomDetail.getRoomNo(), customerId);
							updateRoomForBooking(customerExists.getCustomerId(), roomDetail, bookingRes);
							
						}else if(customerExists!=null && customerExists.getCurrentStatus().equalsIgnoreCase(HotelBookingConstants.IN)) {
							//to handle multiple rooms booking by same customer
							LOGGER.info("Customer Already Exists And currently have a Room , adding one more room under his/her name");
							customerRepository.updateCustomerStatusAndRoomId(HotelBookingConstants.IN, customerExists.getRoomNo()+","+roomDetail.getRoomNo(), customerId);
							updateRoomForBooking(customerExists.getCustomerId(), roomDetail, bookingRes);
						}
					}
				break;
				}else {
					LOGGER.info("Rooms with the requested facilities are occupied");
					bookingRes.setDescription(HotelBookingConstants.ROOMS_WITH_REQUESTED_SPEC_UNAVAILABLE);
				}
			}
		}
		}catch(BadRequestException e) {
			LOGGER.error("no Rooms Available for Requested Facility / facilities value invalid");
			throw e;
		}
		LOGGER.info("Exiting bookRooms");
		return bookingRes;
	}
	
	//to check if customer already exists with given details
	private Integer getCutomerIfExists(CustomerModel customer) {
		LOGGER.info("Entering getCutomerIfExists with Customer detail : {}",customer.toString());
		Integer custId=0;
		Optional<CustomerEntity> customerIfExist= customerRepository.findByCustomerNameAndAgeAndCityAndState(customer.getCustomerName(),
				customer.getAge(),customer.getCity(),customer.getState());
		if(customerIfExist.isPresent()) {
			custId= customerIfExist.get().getCustomerId();
		}
		LOGGER.info("Exiting getCutomerIfExists");
		return custId;
	}
	
	//Update the room status and custId for room booking
	private void updateRoomForBooking(Integer customerId, HotelOwnerEntity roomDetail, BookingResponseModel b) {
		LOGGER.info("Entering updateRoomForBooking with customerId : {}",customerId);
		hotelOwnerRepository.updateRoomStatusAndCustId(HotelBookingConstants.UNAVAILABLE, customerId, roomDetail.getId());
		b.setDescription(HotelBookingConstants.BOOKING_SUCCESSFUL+roomDetail.getRoomNo()+ " Price : "+roomDetail.getPrice() +HotelBookingConstants.PER_NIGHT);
		LOGGER.info("Exiting updateRoomForBooking");
	}
	
	//gets available facilities in hotel
	public FacilitiesModel getRoomSpec(){
		LOGGER.info("Entering getRoomSpec: ");
		FacilitiesModel facilities=new FacilitiesModel();
		facilities.setAirConditioner("YES");
		facilities.setDoubleCot("YES");
		facilities.setWifi("YES");
		LOGGER.info("Exiting getRoomSpec");
		return facilities;
	}
	
}
