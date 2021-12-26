package com.hotelbooking.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hotelbooking.exception.BadRequestException;
import com.hotelbooking.model.BookingRequestModel;
import com.hotelbooking.model.BookingResponseModel;
import com.hotelbooking.model.FacilitiesModel;
import com.hotelbooking.service.CustomerService;

@RestController
@RequestMapping(value= "/customer")
public class CustomerController {
	
	private static final Logger LOGGER=LoggerFactory.getLogger(CustomerController.class);
	
	@Autowired
	private CustomerService customerService;
	
	@PostMapping(value="/book", produces = "application/json")
	public BookingResponseModel bookRooms(@RequestBody BookingRequestModel bookingReq) throws BadRequestException {
		BookingResponseModel bookingResponse=null;
		try {
			bookingResponse=customerService.bookRooms(bookingReq);
		}catch(BadRequestException e) {
			LOGGER.error("BadRequestException occured : {}",e.getMessage());
			throw e;
		}catch(Exception e) {
			LOGGER.error("Exception Occured : {}",e.getMessage());
		}
		return bookingResponse;
	}
	
	@PostMapping(value="/getroomswithspec")
	public List<String> getRoomsBasedOnSpec(@RequestBody FacilitiesModel facilities) throws Exception{
		List<String> response=null;
		try {
			response=customerService.getRoomsBasedOnCriteria(facilities);
		}catch(Exception e) {
			LOGGER.error("Exception occured : {}",e.getMessage());
			throw e;
		}
		return response;
	}
	
	@PostMapping(value="/getroomsprice")
	public List<String> getRoomsPriceOnSpec(@RequestBody FacilitiesModel facilities) throws BadRequestException{
		List<String> response=null;
		try {
			response=customerService.getRoomsPriceOnCriteria(facilities);
		}catch(Exception e) {
			LOGGER.error("Exception occured : {}",e.getMessage());
			throw e;
		}
		return response;
	}
	
	@GetMapping(value= "/getspecs")
	public FacilitiesModel getRoomSpecs(){
		FacilitiesModel facilities=null;
		try {
			facilities=customerService.getRoomSpec();
		}catch(Exception e) {
			LOGGER.error("Exception occured : {}",e.getMessage());
		}
		return facilities;
	}
}
