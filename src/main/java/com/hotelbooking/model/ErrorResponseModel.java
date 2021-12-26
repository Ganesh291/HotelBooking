package com.hotelbooking.model;

import java.util.Date;

import org.springframework.http.HttpStatus;

public class ErrorResponseModel {

	private String errorCode;
	private String errorCategory;
	private String message;
	private HttpStatus httpStatus;
	private String uri;
	public ErrorResponseModel(String errorCode, String errorCategory, String message, HttpStatus httpStatus,
			String uri) {
		super();
		this.errorCode = errorCode;
		this.errorCategory = errorCategory;
		this.message = message;
		this.httpStatus = httpStatus;
		this.uri = uri;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorCategory() {
		return errorCategory;
	}
	public void setErrorCategory(String errorCategory) {
		this.errorCategory = errorCategory;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	
}
