package com.hotelbooking.utility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class HotelBookingUtility {

	public static String mapToJson(Object object) throws JsonProcessingException{
		ObjectMapper objMapper=new ObjectMapper();
		return objMapper.writeValueAsString(object);
	}
}
