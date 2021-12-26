package com.hotelbooking.serviceTest;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.lenient;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyInt;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hotelbooking.entity.CustomerEntity;
import com.hotelbooking.entity.HotelOwnerEntity;
import com.hotelbooking.exception.BadRequestException;
import com.hotelbooking.model.BookingRequestModel;
import com.hotelbooking.model.CustomerModel;
import com.hotelbooking.model.FacilitiesModel;
import com.hotelbooking.repository.CustomerRepository;
import com.hotelbooking.repository.HotelOwnerRepository;
import com.hotelbooking.service.CustomerService;
import com.hotelbooking.utility.HotelBookingConstants;

@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceTest {
	
	private static final Logger LOGGER=LoggerFactory.getLogger(CustomerServiceTest.class);
	
	@InjectMocks
	private CustomerService customerService;
	
	@Mock
	private HotelOwnerRepository hotelOwnerRepository;
	
	@Mock
	private CustomerRepository customerRepository;
	
	FacilitiesModel facilities=new FacilitiesModel();
	List<String> roomLists=new ArrayList<>();
	List<String> priceList=new ArrayList<>();
	HotelOwnerEntity hotelEnty=new HotelOwnerEntity();
	List<HotelOwnerEntity> entyList=new ArrayList<>();
	CustomerModel custModel=new CustomerModel();
	BookingRequestModel bookingReq=new BookingRequestModel();
	CustomerEntity custEnty=new CustomerEntity();

	
	@Before
	public void setUp() {
		facilities.setAirConditioner("YES");
		facilities.setDoubleCot("NO");
		facilities.setWifi("YES");
		roomLists.add("R101");
		priceList.add("R101 - Rs. 1500"+HotelBookingConstants.PER_NIGHT);
		hotelEnty.setRoomNo("R101");
		hotelEnty.setPrice(1500);
		entyList.add(hotelEnty);
		custModel.setCustomerId(1);
		custModel.setCustomerName("STEVE");
		custModel.setAge(23);
		custModel.setCity("CHENNAI");
		custModel.setState("TAMILNADU");
		bookingReq.setFacilities(facilities);
		bookingReq.setCustomer(custModel);
		custEnty.setCustomerId(2);
	}
	
	@Test
	public void getRoomsBasedOnCriteriaTest1() {
		try {
			//Invalid facility value
			facilities.setAirConditioner("OK");
			Assert.assertThrows(BadRequestException.class, ()-> customerService.getRoomsBasedOnCriteria(facilities));
		}catch(Exception e) {
			LOGGER.error("Exception Occured : {}",e.getMessage());
			fail();
		}
	}
	
	@Test
	public void getRoomsBasedOnCriteriaTest2() {
		try {
			//No Rooms for asked facilities
			Assert.assertThrows(BadRequestException.class, ()-> customerService.getRoomsBasedOnCriteria(facilities));
		}catch(Exception e) {
			LOGGER.error("Exception Occured : {}",e.getMessage());
			fail();
		}
	}
	
	@Test
	public void getRoomsBasedOnCriteriaTest3() {
		try {
			//Rooms available with facilities positive test case
			lenient().when(hotelOwnerRepository.findByAirConditionerAndWifiAndDoubleCot(anyString(),anyString(),anyString())).thenReturn(entyList);
			Assert.assertEquals(roomLists, customerService.getRoomsBasedOnCriteria(facilities));
		}catch(Exception e) {
			LOGGER.error("Exception Occured : {}",e.getMessage());
			fail();
		}
	}
	
	@Test
	public void getRoomsPriceOnCriteriaTest1() {
		try {
			//Invalid facility value
			facilities.setAirConditioner("OK");
			Assert.assertThrows(BadRequestException.class, ()-> customerService.getRoomsPriceOnCriteria(facilities));
		}catch(Exception e) {
			LOGGER.error("Exception Occured : {}",e.getMessage());
			fail();
		}
	}
	
	@Test
	public void getRoomsPriceOnCriteriaTest2() {
		try {
			//No Rooms for asked facilities
			Assert.assertThrows(BadRequestException.class, ()-> customerService.getRoomsPriceOnCriteria(facilities));
		}catch(Exception e) {
			LOGGER.error("Exception Occured : {}",e.getMessage());
			fail();
		}
	}
	
	@Test
	public void getRoomsPriceOnCriteriaTest3() {
		try {
			//Price for room with facilities positive test case
			lenient().when(hotelOwnerRepository.findByAirConditionerAndWifiAndDoubleCot(anyString(),anyString(),anyString())).thenReturn(entyList);
			Assert.assertEquals(priceList,customerService.getRoomsPriceOnCriteria(facilities));
		}catch(Exception e) {
			LOGGER.error("Exception Occured : {}",e.getMessage());
			fail();
		}
	}
	
	@Test
	public void bookRoomsTest1() {
		try {
			//with new customer successfull booking
			facilities.setAirConditioner("YES");
			HotelOwnerEntity roomDetail=new HotelOwnerEntity();
			roomDetail.setStatus(HotelBookingConstants.AVAILABLE);
			lenient().when(hotelOwnerRepository.findByAirConditionerAndWifiAndDoubleCot(anyString(),anyString(),anyString())).thenReturn(entyList);
			lenient().when(hotelOwnerRepository.findByRoomNo(anyString())).thenReturn(roomDetail);
			Assert.assertTrue(customerService.bookRooms(bookingReq).getDescription().contains("booked successfully"));
		}catch(Exception e) {
			LOGGER.error("Exception Occured : {}",e.getMessage());
			fail();
		}
	}
	
	@Test
	public void bookRoomsTest2() {
		try {
			//room with requested facilities is unavailable
			facilities.setAirConditioner("YES");
			HotelOwnerEntity roomDetail=new HotelOwnerEntity();
			roomDetail.setStatus(HotelBookingConstants.UNAVAILABLE);
			lenient().when(hotelOwnerRepository.findByAirConditionerAndWifiAndDoubleCot(anyString(),anyString(),anyString())).thenReturn(entyList);
			lenient().when(hotelOwnerRepository.findByRoomNo(anyString())).thenReturn(roomDetail);
			Assert.assertEquals(HotelBookingConstants.ROOMS_WITH_REQUESTED_SPEC_UNAVAILABLE,customerService.bookRooms(bookingReq).getDescription());
		}catch(Exception e) {
			LOGGER.error("Exception Occured : {}",e.getMessage());
			fail();
		}
		
	}
	
	@Test
	public void bookRoomsTest3() {
		try {
			//with customer already exists and booking successful
			facilities.setAirConditioner("YES");
			HotelOwnerEntity roomDetail=new HotelOwnerEntity();
			roomDetail.setStatus(HotelBookingConstants.AVAILABLE);
			lenient().when(hotelOwnerRepository.findByRoomNo(anyString())).thenReturn(roomDetail);
			custEnty.setCurrentStatus(HotelBookingConstants.OUT);
			lenient().when(hotelOwnerRepository.findByAirConditionerAndWifiAndDoubleCot(anyString(),anyString(),anyString())).thenReturn(entyList);
			lenient().when(customerRepository.findByCustomerNameAndAgeAndCityAndState(anyString(),anyInt(),anyString(),anyString())).thenReturn(Optional.of(custEnty));
			lenient().when(customerRepository.findByCustomerId(anyInt())).thenReturn(custEnty);
			Assert.assertTrue(customerService.bookRooms(bookingReq).getDescription().contains("booked successfully"));
		}catch(Exception e) {
			LOGGER.error("Exception Occured : {}",e.getMessage());
			fail();
		}
	}
	
	@Test
	public void bookRoomsTest4() {
		try {
			//with customer already exists, currently staying and booking another room successful 
			facilities.setAirConditioner("YES");
			HotelOwnerEntity roomDetail=new HotelOwnerEntity();
			roomDetail.setStatus(HotelBookingConstants.AVAILABLE);
			lenient().when(hotelOwnerRepository.findByRoomNo(anyString())).thenReturn(roomDetail);
			custEnty.setCurrentStatus(HotelBookingConstants.IN);
			lenient().when(hotelOwnerRepository.findByAirConditionerAndWifiAndDoubleCot(anyString(),anyString(),anyString())).thenReturn(entyList);
			lenient().when(customerRepository.findByCustomerNameAndAgeAndCityAndState(anyString(),anyInt(),anyString(),anyString())).thenReturn(Optional.of(custEnty));
			lenient().when(customerRepository.findByCustomerId(anyInt())).thenReturn(custEnty);
			Assert.assertTrue(customerService.bookRooms(bookingReq).getDescription().contains("booked successfully"));
		}catch(Exception e) {
			LOGGER.error("Exception Occured : {}",e.getMessage());
			fail();
		}
	}
	
	@Test
	public void getRoomSpecTest() {
		try {
			Assert.assertNotNull(customerService.getRoomSpec());
		}catch(Exception e) {
			LOGGER.error("Exception Occured : {}",e.getMessage());
			fail();
		}
	}

}
