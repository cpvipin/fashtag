package com.org.fashtag.aop;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.aop.AfterReturningAdvice;

import com.org.fashtag.common.AppConstants;
import com.org.fashtag.model.Orders;
import com.org.fashtag.notify.SMSSender;
import com.org.fashtag.util.CommonUtils;

public class SmsNotifiers implements AfterReturningAdvice, AppConstants {

	private Collection pointCuts;

	private static Logger logger = (Logger) Logger
			.getInstance(EmailNotifier.class);

	/**
	 * This constructor is invoked when the singleton for this class is created.
	 * advices.xml *
	 * 
	 * @param methodNames
	 */
	public SmsNotifiers(List pointCuts) {
		this.pointCuts = pointCuts;
	}

	public SmsNotifiers() {

	}

	@Override
	public void afterReturning(Object returnValue, Method method,
			Object[] args, Object target) throws Throwable {
		if (this.pointCuts.contains(method.getName())) {
			if (CommonUtils.isNormalizeStringEquals(method.getName(),
					"signUp")) {
			
				}
			else 	if (CommonUtils.isNormalizeStringEquals(method.getName(),"updateOrderPaymentStatus")) 
			{
				Orders orders=(Orders)args[0];
				if(orders.getOrdersStatus().getName().equals("SUCCESS") && !CommonUtils.isEmpty(orders.getMobile()))
				{
					
				
				
				
				}
			}
			
			}
		}


	public void sendOrderConfirmSms(Orders orders) {

		String mobileNum = orders.getMobile();
		if (!CommonUtils.isEmpty(mobileNum)) {
			StringBuffer message = new StringBuffer();
			message.append("Hi, Thank you for shopping with us. Your Order is confirmed with order id "
					+ orders.getId());
			message.append("Track your order from fashtag using this. "
					+ orders.getId());
			sendSMS(message.toString(), mobileNum);
		}
	}

	public void sendSMS(String message, String mobileNum) {
		if (message.length() <= 250) {
			SMSSender.getSoleInstance().sendMessage(mobileNum, message);
		} else {
			int rem = message.length() % 250;
			if (rem > 0) {
				sendSMS(message.substring(0, 250), mobileNum);
				sendSMS(message.substring(251, message.length()), mobileNum);
			} else {
				SMSSender.getSoleInstance().sendMessage(mobileNum, message);
			}
		}

	}

}
