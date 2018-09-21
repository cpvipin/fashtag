package com.org.fashtag.util;

import java.util.Collection;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
* @author(name="vipin c p") 
*/
public final class CommonUtils {
	
	

	/**
	 * This method returns true if the input string is either null or has a
	 * trimmed length of 0
	 * @param aSource
	 * @return boolean
	 */
	public static boolean isEmpty(String aSource) {
		boolean tResult = true;
		if (aSource != null && aSource.trim().length() > 0) {
			tResult = false;
		}
		return tResult;

	}
	
	public static boolean  isInteger(String str)
	{
		try{
		
			Integer strval=Integer.parseInt(str);
			if(strval.intValue()>0)
				return true;
			else
				return false;
			
		}
		catch(NumberFormatException ne)
		{
			return false;
		}
	}
	
	/**
	 * This method returns a normalized string. If a null string is passed, then
	 * it returns empty string, other wise trims the string.
	 * 
	 * @param aSource
	 * @return String
	 */
	public static String normalize(String aSource) {

		return ((aSource == null) ? "" : aSource.trim());

	}
	
	/**
	 * @param id
	 * @param id2
	 * @return
	 */
	public static boolean nullSafeObjectEquals(Object obj1, Object obj2) {
		if (obj1 == null && obj2 == null) {
			return true;
		}
		if (obj1 != null && obj2 != null) {
			if (obj1.equals(obj2)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * This method replaces all occurances of 'old' string with 'new' string
	 * This method should work in all cases, without any infinite loop. changed
	 * the method to use the new replaceAll method
	 * 
	 * @param aSource
	 * @param aOld
	 * @param aNew
	 * @return String
	 */
	public static String replaceAll(String aSource, String aOld, String aNew) {
		if (aSource == null || aOld == null || aNew == null) {
			return aSource;
		}
		return aSource.replaceAll(aOld, aNew);
	}
	
	public static boolean isEmpty(Collection aCollection) {
		if (aCollection == null) {
			return true;
		} else if (aCollection.size() == 0) {
			return true;
		}
		return false;
	}
	
	
	public static String base64Encode(String str) {
		String encoded = null;
		try {
			
			BASE64Encoder encoder = new BASE64Encoder();
			encoded = encoder.encode(str.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return encoded;
	}
	
	public static String base64Decode(String str) {
		String decoded = null;
		try {
			
			BASE64Decoder decoder = new BASE64Decoder();
			decoded = decoder.decodeBuffer(str).toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return decoded;
	}

	public static boolean isNormalizeStringEquals(String firstString,
			String secondString) {
		if (firstString == null || secondString == null) {
			return false;
		}
		if (normalize(firstString).equals(normalize(secondString))) {
			return true;
		}
		return false;
	}
	
	

}
