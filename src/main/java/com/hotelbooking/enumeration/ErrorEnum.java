package com.hotelbooking.enumeration;

public enum ErrorEnum {
	INVALID_FACILITY_VALUES("SAMP001",ErrorCategory.BADREQUEST.name(),"Given values for facility is invalid , values should be YES or NO"),
	NO_ROOMS_WITH_SPEC("SAMP002",ErrorCategory.BADREQUEST.name(),"Sorry! No Rooms Available for this given specification");
	
	private String code;
	private String category;
	private String description;
	
	ErrorEnum(String code, String category, String description) {
		// TODO Auto-generated constructor stub
		this.code=code;
		this.category=category;
		this.description=description;	
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	


}
