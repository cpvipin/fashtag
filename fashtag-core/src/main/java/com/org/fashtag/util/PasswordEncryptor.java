package com.org.fashtag.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import sun.misc.BASE64Encoder;


public final class PasswordEncryptor {

	private static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	private static SecureRandom rnd = new SecureRandom();
	private static int passwordLength=5;
	
	public static String generateRandomPassword()
	{

		StringBuilder password = new StringBuilder( passwordLength );
		   for( int i = 0; i < passwordLength; i++ ) 
			   password.append( AB.charAt( rnd.nextInt(AB.length()) ) );
		   
		   return password.toString();
		
	}
	
	
	public static String encrypt(String password) {

		String encoded = null;
		try {
			byte[] secret = generate(CommonUtils.normalize(password));
			BASE64Encoder encoder = new BASE64Encoder();
			encoded = encoder.encode(secret);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return encoded;

	}
	
	
	

	/**
	 * @param password
	 * @return
	 */
	private static byte[] generate(String password)
			throws NoSuchAlgorithmException {
		MessageDigest d = null;
		d = MessageDigest.getInstance("SHA-1");
		d.reset();
		d.update(password.getBytes());
		return d.digest();
	}
}
