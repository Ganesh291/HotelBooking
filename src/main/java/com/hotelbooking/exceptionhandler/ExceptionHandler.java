package com.hotelbooking.exceptionhandler;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.hotelbooking.enumeration.ErrorEnum;
import com.hotelbooking.exception.BadRequestException;
import com.hotelbooking.model.ErrorResponseModel;

@RestControllerAdvice
public class ExceptionHandler {
	
	@org.springframework.web.bind.annotation.ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ErrorResponseModel> handleBadRequest(HttpServletRequest req, BadRequestException ex){
		ErrorResponseModel errorResp=new ErrorResponseModel(ErrorEnum.INVALID_FACILITY_VALUES.getCode(),ErrorEnum.INVALID_FACILITY_VALUES.getCategory(), 
				ErrorEnum.INVALID_FACILITY_VALUES.getDescription(), HttpStatus.BAD_REQUEST, req.getRequestURI());
		
		return new ResponseEntity<ErrorResponseModel>(errorResp,HttpStatus.BAD_REQUEST);
	}

}
