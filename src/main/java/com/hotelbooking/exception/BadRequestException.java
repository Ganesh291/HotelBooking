package com.hotelbooking.exception;

import com.hotelbooking.enumeration.ErrorEnum;

public class BadRequestException extends Exception {
	
	private static final long serialVersionUID =-3992797190692194686L;
	
	final ErrorEnum error;
	
	public BadRequestException(ErrorEnum error, String rawMessage) {
		super(rawMessage);
		this.error=error;
	}

	public ErrorEnum getError() {
		return error;
	}

}
