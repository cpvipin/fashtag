package com.org.fashtag.common;

public enum UserPreference {
	
		WISHLIST("WISHLIST"),
		PROFILE("PROFILE"),
		ORDERS("ORDERS"),
		RETURNS("RETURNS"),
		INFO("INFO"),
		RETURNORDER("RETURNORDER"),
		ADDRESS("ADDRESS");
		
		
		private String resource;
		
		UserPreference(String resource)
		{
			this.resource=resource;
		}
		public String getResource()
		{
			return resource;
		}
		
		
	

}
