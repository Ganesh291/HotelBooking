package com.hotelbooking.serviceTest;


import static org.junit.Assert.fail;
import static org.mockito.Mockito.lenient;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyInt;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hotelbooking.entity.CustomerEntity;
import com.hotelbooking.entity.HotelOwnerEntity;
import com.hotelbooking.model.HotelOwnerModel;
import com.hotelbooking.repository.CustomerRepository;
import com.hotelbooking.repository.HotelOwnerRepository;
import com.hotelbooking.service.HotelOwnerService;
import com.hotelbooking.utility.HotelBookingConstants;

@RunWith(MockitoJUnitRunner.class)
public class HotelOwnerServiceTest {
	
	private static final Logger LOGGER=LoggerFactory.getLogger(HotelOwnerServiceTest.class);
	
	@InjectMocks
	private HotelOwnerService hotelOwnerService;
	
	@Mock
	private HotelOwnerRepository hotelOwnerRepository;
	
	@Mock
	private CustomerRepository customerRepository;
	
	@Test
	public void getCustomerDetailsforRoomTest1() {
		try {
			//with customerId testing
			HotelOwnerEntity hotelEnty=new HotelOwnerEntity();
			hotelEnty.setCustomerId(1);
			lenient().when(hotelOwnerRepository.findByRoomNo(anyString())).thenReturn(hotelEnty);
			lenient().when(customerRepository.findByCustomerId(anyInt())).thenReturn(new CustomerEntity());
			Assert.assertNull(hotelOwnerService.getCustomerDetailsforRoom("R101").getDescription());
		}catch(Exception e) {
			LOGGER.error("Exception Occured : {}",e.getMessage());
			fail();
		}
	}
	
	@Test
	public void getCustomerDetailsforRoomTest2() {
		try {
			//null customerId testing to cover else
			lenient().when(hotelOwnerRepository.findByRoomNo(anyString())).thenReturn(new HotelOwnerEntity());
			Assert.assertEquals(HotelBookingConstants.ROOM_IS_AVAILABLE, hotelOwnerService.getCustomerDetailsforRoom("R101").getDescription());
		}catch(Exception e) {
			LOGGER.error("Exception Occured : {}",e.getMessage());
			fail();
		}
	}
	
	@Test
	public void getAvailableRoomsTest() {
		try {
			lenient().when(hotelOwnerRepository.findByStatus(anyString())).thenReturn(new ArrayList<>());
			Assert.assertEquals(new ArrayList<>(), hotelOwnerService.getAvailableRooms());
		}catch(Exception e) {
			LOGGER.error("Exception Occured : {}",e.getMessage());
			fail();
		}
	}
	
	@Test
	public void newRoomAddTest() {
		try {
			HotelOwnerModel hotelModel=new HotelOwnerModel();
			hotelModel.setRoomNo("R234");
			List<HotelOwnerModel> hotelModelList=new ArrayList<>();
			hotelModelList.add(hotelModel);
			Assert.assertEquals(HotelBookingConstants.SUCCESS, hotelOwnerService.newRoomAdd(hotelModelList));
		}catch(Exception e) {
			LOGGER.error("Exception Occured : {}",e.getMessage());
			fail();
		}
	}
	
	@Test
	public void getAllCustomersTest() {
		try {
			lenient().when(customerRepository.findAll()).thenReturn(new ArrayList<>());
			Assert.assertEquals(new ArrayList<>(), hotelOwnerService.getAllCustomers());
		}catch(Exception e) {
			LOGGER.error("Exception Occured : {}",e.getMessage());
			fail();
		}
	}
	
	@Test
	public void getCustomersStayedTest() {
		try {
			lenient().when(customerRepository.findByCurrentStatus(anyString())).thenReturn(new ArrayList<>());
			Assert.assertEquals(new ArrayList<>(), hotelOwnerService.getCustomersStayed());
		}catch(Exception e) {
			LOGGER.error("Exception Occured : {}",e.getMessage());
			fail();
		}
	}
	

}
