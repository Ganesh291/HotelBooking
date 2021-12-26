package com.hotelbooking.controllerTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.lenient;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.hotelbooking.controller.HotelOwnerController;
import com.hotelbooking.model.CustDetailResModel;
import com.hotelbooking.model.HotelOwnerModel;
import com.hotelbooking.service.HotelOwnerService;
import com.hotelbooking.utility.HotelBookingUtility;

@RunWith(SpringRunner.class)
@WebMvcTest(value=HotelOwnerController.class)
public class HotelOwnerControllerTest {
	
	private static final Logger LOGGER=LoggerFactory.getLogger(HotelOwnerControllerTest.class);
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	HotelOwnerService hotelOwnerService;
	
	String getAvailableRoomsURI="/hotelowner/availablerooms";
	String getCustDetailURI= "/hotelowner/custdetail/R101";
	String insertNewRoomURI="/hotelowner/newroom";
	String customerStayedGetURI="/hotelowner/custstayed";
	String getAllCustomerURI="/hotelowner/allcustomers";
	String setupRoomDetails="/hotelowner/newroom";
	String customerDetailsExportURI="/hotelowner/allcustomers/pdf";
    
	
	@Test
	public void getspecsTest() {
		try {

			lenient().when(hotelOwnerService.getAvailableRooms()).thenReturn(new ArrayList<>());
			RequestBuilder requestBuilder = MockMvcRequestBuilders
					.get(getAvailableRoomsURI)
					.contentType(MediaType.APPLICATION_JSON);
			
			MvcResult result=mockMvc.perform(requestBuilder).andReturn();
			MockHttpServletResponse response=result.getResponse();
			assertEquals(HttpStatus.OK.value(), response.getStatus());
			
		}catch(Exception e) {
			LOGGER.error("Exception occured: {}",e.getMessage());
			fail();
		}
	}
	
	@Test
	public void getCustDetailTest() {
		try {

			lenient().when(hotelOwnerService.getCustomerDetailsforRoom(any(String.class))).thenReturn(new CustDetailResModel());
			RequestBuilder requestBuilder = MockMvcRequestBuilders
					.get(getCustDetailURI)
					.contentType(MediaType.APPLICATION_JSON);
			
			MvcResult result=mockMvc.perform(requestBuilder).andReturn();
			MockHttpServletResponse response=result.getResponse();
			assertEquals(HttpStatus.OK.value(), response.getStatus());
			
		}catch(Exception e) {
			LOGGER.error("Exception occured: {}",e.getMessage());
			fail();
		}
	}
	
	@Test
	public void getCustStayedTest() {
		try {

			lenient().when(hotelOwnerService.getCustomersStayed()).thenReturn(new ArrayList<>());
			RequestBuilder requestBuilder = MockMvcRequestBuilders
					.get(customerStayedGetURI)
					.contentType(MediaType.APPLICATION_JSON);
			
			MvcResult result=mockMvc.perform(requestBuilder).andReturn();
			MockHttpServletResponse response=result.getResponse();
			assertEquals(HttpStatus.OK.value(), response.getStatus());
			
		}catch(Exception e) {
			LOGGER.error("Exception occured: {}",e.getMessage());
			fail();
		}
	}
	
	@Test
	public void getAllCustomerTest() {
		try {

			lenient().when(hotelOwnerService.getAllCustomers()).thenReturn(new ArrayList<>());
			RequestBuilder requestBuilder = MockMvcRequestBuilders
					.get(getAllCustomerURI)
					.contentType(MediaType.APPLICATION_JSON);
			
			MvcResult result=mockMvc.perform(requestBuilder).andReturn();
			MockHttpServletResponse response=result.getResponse();
			assertEquals(HttpStatus.OK.value(), response.getStatus());
			
		}catch(Exception e) {
			LOGGER.error("Exception occured: {}",e.getMessage());
			fail();
		}
	}
	
	@Test
	public void setupRoomDetailsTest() {
		try {
			HotelOwnerModel hotelModel=new HotelOwnerModel();
			hotelModel.setRoomNo("R110");
			hotelModel.setAirConditioner("YES");
			hotelModel.setDoubleCot("YES");
			hotelModel.setDoubleCot("YES");
			hotelModel.setPrice(2000);
			List<HotelOwnerModel> hotelModelList = new ArrayList<>();
			hotelModelList.add(hotelModel);
			String inputJson=HotelBookingUtility.mapToJson(hotelModelList);
			
			lenient().when(hotelOwnerService.newRoomAdd(any())).thenReturn("SUCCESS");
			RequestBuilder requestBuilder = MockMvcRequestBuilders
					.post(insertNewRoomURI)
					.accept(MediaType.APPLICATION_JSON).content(inputJson)
					.contentType(MediaType.APPLICATION_JSON);
			
			MvcResult result=mockMvc.perform(requestBuilder).andReturn();
			MockHttpServletResponse response=result.getResponse();
			String output=response.getContentAsString();
			assertThat(output).isEqualTo("SUCCESS");
			assertEquals(HttpStatus.OK.value(), response.getStatus());
			
		}catch(Exception e) {
			LOGGER.error("Exception occured: {}",e.getMessage());
			fail();
		}
	}
	
	@Test
	public void exportAllCustomerTest() {
		try {

			lenient().when(hotelOwnerService.getAllCustomers()).thenReturn(new ArrayList<>());
			RequestBuilder requestBuilder = MockMvcRequestBuilders
					.get(customerDetailsExportURI)
					.contentType(MediaType.APPLICATION_JSON);
			
			MvcResult result=mockMvc.perform(requestBuilder).andReturn();
			MockHttpServletResponse response=result.getResponse();
			assertEquals(HttpStatus.OK.value(), response.getStatus());
			
		}catch(Exception e) {
			LOGGER.error("Exception occured: {}",e.getMessage());
			fail();
		}
	}
}