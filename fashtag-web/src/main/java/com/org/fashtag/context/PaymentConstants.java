package com.org.fashtag.context;

public interface PaymentConstants {
	
	public static final String PG_MERCHANT_ID ="*******"; // change with your value
	public static final String PG_ACCESS_CODE ="**********************"; // change with your value
	public static final String PG_WORKING_KEY ="*********************"; // change with your value
	/*public static final String PG_ACCESS_CODE ="***********************"; // change with your value
	public static final String PG_WORKING_KEY ="**************************";*/ // change with your value
	public static final String PG_CURRENCY ="INR";
	public static final String PG_LANGUAGE ="EN";
	public static final String PG_INTEGRATION_TYPE ="normal";		
	public static final String PG_REDIRECT_URL ="https://XXXXXXXXX.XXXXXXXXXX.com/paymentFin.htm";
	public static final String PG_CANCEL_URL ="https://XXXXXXXXX.XXXXXXXXXX.com/paymentFin.htm"; // change with your url
	public static String PG_URL="https://secure.ccavenue.com/transaction/transaction.do?command=initiateTransaction";
	public static int PAYMENT_TYPE_ONLINE=0;
	public static int PAYMENT_TYPE_COD=1;
}
