package com.org.fashtag.common;

public enum ResponseStatus {
	
	SUCCESS("SUCCESS"),
	ERROR("ERROR"),
	NONE("NONE");

	private String code;
	
	ResponseStatus(String code)
	{
		this.code=code;
	}
	public String getCode()
	{
		return code;
	}
	

}
