package com.hotelbooking.controllerTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.lenient;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import static org.mockito.ArgumentMatchers.any;
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
import com.hotelbooking.controller.CustomerController;
import com.hotelbooking.enumeration.ErrorEnum;
import com.hotelbooking.exception.BadRequestException;
import com.hotelbooking.model.BookingRequestModel;
import com.hotelbooking.model.BookingResponseModel;
import com.hotelbooking.model.CustomerModel;
import com.hotelbooking.model.FacilitiesModel;
import com.hotelbooking.service.CustomerService;
import com.hotelbooking.utility.HotelBookingConstants;
import com.hotelbooking.utility.HotelBookingUtility;
import com.jayway.jsonpath.internal.function.text.Length;

@RunWith(SpringRunner.class)
@WebMvcTest(value=CustomerController.class)
public class CustomerControllerTest {
	
	private static final Logger LOGGER=LoggerFactory.getLogger(CustomerControllerTest.class);
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	CustomerService customerService;
	
	BookingRequestModel bookingReq=new BookingRequestModel();
	CustomerModel custModel=new CustomerModel();
	FacilitiesModel facilities = new FacilitiesModel();
	String bookURI="/customer/book";
	String getRoomsBasedOnSpecURI="/customer/getroomswithspec";
	String getroomspriceURI="/customer/getroomsprice";
	String getspecsURI="/customer/getspecs";
	
	@Before
	public void setUp() {
		custModel.setAge(27);
		custModel.setCity("CHENNAI");
		custModel.setState("TAMILNADU");
		custModel.setCustomerName("TONY");
		facilities.setAirConditioner("YES");
		facilities.setDoubleCot("YES");
		facilities.setWifi("YES");
		bookingReq.setCustomer(custModel);
		bookingReq.setFacilities(facilities);
	}
	
	@Test
	public void bookRoomsSuccessTest()  {
		try{
			//booking api test for success scenario
			String inputString=HotelBookingUtility.mapToJson(bookingReq);
			
			BookingResponseModel res=new BookingResponseModel();
			res.setDescription(HotelBookingConstants.BOOKING_SUCCESSFUL+ "R107 Price : 2000"+HotelBookingConstants.PER_NIGHT);
			res.setRoomNo("R107");
			
			String outputString=HotelBookingUtility.mapToJson(res);
			
			lenient().when(customerService.bookRooms(Mockito.any())).thenReturn(res);
			
			RequestBuilder requestBuilder = MockMvcRequestBuilders
					.post(bookURI)
					.accept(MediaType.APPLICATION_JSON).content(inputString)
					.contentType(MediaType.APPLICATION_JSON);
			
			MvcResult result=mockMvc.perform(requestBuilder).andReturn();
			MockHttpServletResponse response=result.getResponse();
			
			String output=response.getContentAsString();
			LOGGER.info("output : {}",output);
			assertThat(outputString).isEqualTo(output);
			assertEquals(HttpStatus.OK.value(), response.getStatus());
			
		}catch(Exception e) {
			LOGGER.error("Exception occured: {}",e.getMessage());
			fail();
		}
	}
	
	@Test
	public void bookRoomsExceptionTest()  {
		try{
			//booking api test for failure scenario
			String inputString=HotelBookingUtility.mapToJson(bookingReq);
			
			lenient().when(customerService.bookRooms(Mockito.any())).thenThrow(new BadRequestException(ErrorEnum.INVALID_FACILITY_VALUES,""));
			
			RequestBuilder requestBuilder = MockMvcRequestBuilders
					.post(bookURI)
					.accept(MediaType.APPLICATION_JSON).content(inputString)
					.contentType(MediaType.APPLICATION_JSON);
			
			MvcResult result=mockMvc.perform(requestBuilder).andReturn();
			MockHttpServletResponse response=result.getResponse();

			assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
		}catch(Exception e) {
			LOGGER.error("Exception occured: {}",e.getMessage());
			fail();
		}
	}
	
	@Test
	public void getRoomBasedOnSpecSuccessTest() {
		try {
			//api test for getrooms with success
			String inputString=HotelBookingUtility.mapToJson(facilities);
			lenient().when(customerService.getRoomsBasedOnCriteria(any(FacilitiesModel.class))).thenReturn(new ArrayList<>());
			
			RequestBuilder requestBuilder = MockMvcRequestBuilders
					.post(getRoomsBasedOnSpecURI)
					.accept(MediaType.APPLICATION_JSON).content(inputString)
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
	public void getRoomBasedOnSpecExceptionTest() {
		try {
			//api test for getrooms with success
			String inputString=HotelBookingUtility.mapToJson(facilities);
			lenient().when(customerService.getRoomsBasedOnCriteria(any(FacilitiesModel.class))).thenThrow(new BadRequestException(ErrorEnum.NO_ROOMS_WITH_SPEC,""));
			
			RequestBuilder requestBuilder = MockMvcRequestBuilders
					.post(getRoomsBasedOnSpecURI)
					.accept(MediaType.APPLICATION_JSON).content(inputString)
					.contentType(MediaType.APPLICATION_JSON);
			
			MvcResult result=mockMvc.perform(requestBuilder).andReturn();
			MockHttpServletResponse response=result.getResponse();
			assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
			
		}catch(Exception e) {
			LOGGER.error("Exception occured: {}",e.getMessage());
			fail();
		}
	}
	
	@Test
	public void getroomspriceSuccessTest() {
		try {
			//api test for getprice for rooms with success
			String inputString=HotelBookingUtility.mapToJson(facilities);
			lenient().when(customerService.getRoomsPriceOnCriteria(any(FacilitiesModel.class))).thenReturn(new ArrayList<>());
			
			RequestBuilder requestBuilder = MockMvcRequestBuilders
					.post(getroomspriceURI)
					.accept(MediaType.APPLICATION_JSON).content(inputString)
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
	public void getroomspriceExceptionTest() {
		try {
			//api test for getprice for room with Exception
			String inputString=HotelBookingUtility.mapToJson(facilities);
			lenient().when(customerService.getRoomsPriceOnCriteria(any(FacilitiesModel.class))).thenThrow(new BadRequestException(ErrorEnum.NO_ROOMS_WITH_SPEC,""));
			
			RequestBuilder requestBuilder = MockMvcRequestBuilders
					.post(getroomspriceURI)
					.accept(MediaType.APPLICATION_JSON).content(inputString)
					.contentType(MediaType.APPLICATION_JSON);
			
			MvcResult result=mockMvc.perform(requestBuilder).andReturn();
			MockHttpServletResponse response=result.getResponse();
			assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
			
		}catch(Exception e) {
			LOGGER.error("Exception occured: {}",e.getMessage());
			fail();
		}
	}
	
	@Test
	public void getspecsTest() {
		try {
			lenient().when(customerService.getRoomSpec()).thenReturn(facilities);
			
			RequestBuilder requestBuilder = MockMvcRequestBuilders
					.get(getspecsURI)
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
