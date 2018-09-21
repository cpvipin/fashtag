package com.org.fashtag.common;

public enum Gender {

	FEMALE(1),	
	MALE(2),
	UNKNOWN(3);

	private int value;
	
	Gender(int value)
	{
		this.value=value;
	}
	public int getValue()
	{
		return value;
	}
	

}
