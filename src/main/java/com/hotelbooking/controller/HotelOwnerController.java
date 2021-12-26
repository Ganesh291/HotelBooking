package com.hotelbooking.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotelbooking.entity.CustomerEntity;
import com.hotelbooking.entity.HotelOwnerEntity;
import com.hotelbooking.model.CustDetailResModel;
import com.hotelbooking.model.HotelOwnerModel;
import com.hotelbooking.model.UserPDFExporter;
import com.hotelbooking.service.HotelOwnerService;
import com.lowagie.text.DocumentException;

@RestController
@RequestMapping(value= "/hotelowner") 
public class HotelOwnerController {
	
	private static final Logger LOGGER=LoggerFactory.getLogger(HotelOwnerController.class);
	
	@Autowired
	private HotelOwnerService hotelOwnerService;
	
	@GetMapping(value= "/availablerooms" , produces = "application/json")
	public List<HotelOwnerEntity> getAvailableRooms() {
		List<HotelOwnerEntity> heList=null;
		try {
			heList=hotelOwnerService.getAvailableRooms();
		}catch(Exception e) {
			LOGGER.error("Exception occured: {}",e.getMessage());
		}
		return heList;
	}
	
	@GetMapping(value="/custdetail/{roomno}")
	public CustDetailResModel getCustDetailOnEachRoom(@PathVariable String roomno){
		CustDetailResModel response=null;
		try {
			response=hotelOwnerService.getCustomerDetailsforRoom(roomno);
		}catch(Exception e) {
			LOGGER.error("Exception occured: {}",e.getMessage());
		}
		return response;
	}
	
	@GetMapping(value = "/custstayed")
	public List<CustomerEntity> getCustomerStayed(){
		List<CustomerEntity> custEntyList=null;
		try {
			custEntyList=hotelOwnerService.getCustomersStayed();
		}catch(Exception e) {
			LOGGER.error("Exception occured: {}",e.getMessage());
		}
		return custEntyList;
	}
	
	@PostMapping(value="/newroom")
	public String setupRoomDetails(@RequestBody List<HotelOwnerModel> hotelModel) {
		String response="";
		try {
			response=hotelOwnerService.newRoomAdd(hotelModel);
		}catch(Exception e) {
			LOGGER.error("Exception occured: {}",e.getMessage());
			return "Failed";
		}
		return response;
	}

	@GetMapping(value = "/allcustomers")
	public List<CustomerEntity> getAllCustomers(){
		List<CustomerEntity> response=null;
		try {
			response=hotelOwnerService.getAllCustomers();
		}catch(Exception e) {
			LOGGER.error("Exception occured: {}",e.getMessage());
		}
		return response;
	}
	
	@GetMapping(value = "/allcustomers/pdf" , produces = "application/pdf")
	public void getAllCustomersExprt(HttpServletResponse httpResponse) throws DocumentException, IOException{
		/* used to fetch customer details as a printable report 
		 * use this url in chrome " http://localhost:8080/hotelowner/allcustomers/pdf " to download a pdf*/
        httpResponse.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
         
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=CustomersReport_" + currentDateTime + ".pdf";
        httpResponse.setHeader(headerKey, headerValue);
         
        List<CustomerEntity> customerList = hotelOwnerService.getAllCustomers();
         
        UserPDFExporter exporter = new UserPDFExporter(customerList);
        exporter.export(httpResponse);
	}

}
