package com.org.fashtag.aop;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.aop.AfterReturningAdvice;

import com.org.fashtag.common.AppConstants;
import com.org.fashtag.common.AppCoreConstants;
import com.org.fashtag.controller.BaseController;
import com.org.fashtag.model.Customer;
import com.org.fashtag.model.OrderProduct;
import com.org.fashtag.model.Orders;
import com.org.fashtag.model.OrdersStatus;
import com.org.fashtag.model.Product;
import com.org.fashtag.notify.MailSender;
import com.org.fashtag.util.CommonUtils;



public class EmailNotifier implements AfterReturningAdvice,AppConstants {
	
	private Collection pointCuts;
	
	private static Logger logger = (Logger) Logger.getInstance(EmailNotifier.class);
	
	/**
	 * This constructor is invoked when the singleton for this class is created.
	 * advices.xml
	 *  * 
	 * @param methodNames
	 */
	public EmailNotifier(List pointCuts) {
		this.pointCuts = pointCuts;
	}
	
	public EmailNotifier() {
		
	}
	
	@Override
	public void afterReturning(Object returnValue, Method method,
			Object[] args, Object target) throws Throwable {
		if (this.pointCuts.contains(method.getName())) {
		if (CommonUtils.isNormalizeStringEquals(method.getName(),"createCustomer")) 
		{
			Customer customer=(Customer)args[0];
		//	sendConfirmEmail(customer);
		}
		else if (CommonUtils.isNormalizeStringEquals(method.getName(),"updatePaymentStatus")) 
		{
			Orders orders=(Orders)args[0];
			if(orders.getOrdersStatus().getName().equals("PROCESSING") && !CommonUtils.isEmpty(orders.getCustomer().getEmail()))
			{
			sendOrderConfirmEmail(orders);
			}
		}
		
		}

	}
	
	
	public void sendOrderConfirmEmail(Orders orders)
	{
		
		StringBuffer msgBody = new StringBuffer();
		MailSender mailSender = MailSender.getSoleInstance();
		String mailSubject = "Order confirmed";
		
		msgBody.append(getHeader());
		
		msgBody.append("<tr><td><table width='100%' style='background-color: white; vertical-align: top; margin-bottom: 10px;' cellpadding='15' cellspacing='0'>");	
		msgBody.append("<tr><td><div><p><strong>Dear Customer,</strong></p><p>Your Order Id:  <b>"+orders.getId()+"</b></p></div></td></tr></table></td></tr>");	
		msgBody.append("<tr><td><table width='100%' style='background-color: white; vertical-align: top; margin-bottom: 10px;' cellpadding='15' cellspacing='0'>");

		Collection orderProdColl=orders.getOrderProduct();
		
		Iterator iter=orderProdColl.iterator();
		while(iter.hasNext())
		{
			msgBody.append("<tr><td><div>");
			
			OrderProduct orderProd=(OrderProduct) iter.next();
			msgBody.append("<img src='"+BaseController.getAppSystemProperties(AppConstants.PRODUCT_IMAGE_URL)+orderProd.getProduct().getDefaultImage()+"' width='100' height='100' />");
			msgBody.append("</div></td><td><p> <b>Name :</b> ");
			msgBody.append(orderProd.getProduct().getName());
			msgBody.append("<br/><b>Size :</b>");
			msgBody.append(orderProd.getSize().getName());
			msgBody.append("<br/> <b>Qty:</b>");
			msgBody.append(orderProd.getQuantity());
			msgBody.append("<br/></p></td></tr>");
			
		}
		msgBody.append("</table></td></tr>");
		msgBody.append(getFooter());
		mailSender.sendFormattedMessage(BaseController.getAppSystemProperties(AppCoreConstants.SUPPORT_EMAIL), orders.getCustomer().getEmail(),"","", mailSubject,msgBody.toString(),"");	
		
		
	}
	
	
	
	
	public void sendTemporaryPassword(Customer customer,String password)
	{
		StringBuffer msgBody = new StringBuffer();
		MailSender mailSender = MailSender.getSoleInstance();
		String mailSubject = "New Password";
		msgBody.append(getHeader());
		
		msgBody.append("<table width='100%' style='background-color: white; vertical-align: top; margin-bottom: 10px;' cellpadding='15' cellspacing='0'><tr><td><div><p>");
		msgBody.append("<strong>Dear Customer,</strong></p><p><strong></strong><br>Your new password is generated . Please login using below password. .</p>");		
		msgBody.append("<p>Your Password:  <b>"+password+"</b><br/> Please note this is a temporary password and will be expired in 24 hours. <br/> If still you are unable to login Please send us a mail at "+BaseController.getAppSystemProperties(AppCoreConstants.SUPPORT_EMAIL)+"  </p><p>Regards,<br>Fashtag team - Making people beautiful!</p><p><br><br></p></div>");		
		msgBody.append("</td></tr></table>");		
				
		
		msgBody.append(getFooter());
		mailSender.sendFormattedMessage(BaseController.getAppSystemProperties(AppCoreConstants.SUPPORT_EMAIL), customer.getEmail(),"","", mailSubject,msgBody.toString(),"");	
		
		
	}
	
	
	
	
	private String getHeader()
	{
		StringBuffer msgHeader=new StringBuffer();
		msgHeader.append("<!DOCTYPE html><html lang='en'><body style='background-color: #f4f4f5; margin: 0;'>");
		msgHeader.append("<table style='width: 80%; font-family: Arial, Helvetica, sans-serif; font-size: 14px; color: #6f6f6f; line-height: 20px; vertical-align: middle; max-width: 640px; border-top: 10px solid #072b47;' cellpadding='0' cellspacing='0'>");
		msgHeader.append("<tr>");
		msgHeader.append("<td><table width='100%' style='background-color: white; margin-bottom: 10px;' cellpadding='15' cellspacing='0'><tr>");
		msgHeader.append("<td style=''><a href='#'><img src='"+BaseController.getAppSystemProperties(AppCoreConstants.APP_BASE_URL)+"/img/logo.png' height='36' height='156' alt=' '></a></td><td><div style='color: #5e758; font-size: 16px; padding: 25px 0; text-align: right;'>");
		msgHeader.append("www.fashtag.in<br/>24/7 /"+BaseController.getAppSystemProperties(AppConstants.SUPPORT_PHONE)+"</div></td></tr></table></td></tr><tr><td>");
		return msgHeader.toString();
		
	}
	
	private String getFooter()
	{
		StringBuffer msgFooter=new StringBuffer();
		msgFooter.append("</td></tr><tr>");
		msgFooter.append("<td style='background-color: #072b47;'>");
		msgFooter.append("<table width='100%' style='font-size: 12px; color: #b9cede; vertical-align: top;' cellpadding='15' cellspacing='0'>");
		msgFooter.append("<tr><td><div>Copyright &copy; 2014  | All Rights Reserved </div></td><td><div style='text-align: right; margin-top: 40px;'><p>&copy; www.fashtag.in <br>");
		msgFooter.append("<a href='https://fashtag.in' style='color: #b9cede;'>www.fashtag.in</a><br>Phone: "+BaseController.getAppSystemProperties(AppConstants.SUPPORT_PHONE)+"</p></div></td></tr></table>");
		msgFooter.append("<table width='100%' style='vertical-align: top;' cellpadding='15' cellspacing='0'><tr><td>");
		msgFooter.append("<div style='font-size: 12px; text-align: center; color: #7192ac; padding: 10px 15px 0; border-top: 1px solid #041a2b;'>");
		msgFooter.append("You're receiving this email from fashtag. <br><a href='https://fashtag.in' style='color: #7192ac;'>Login here</a></div></td>");
		msgFooter.append("</tr></table></td></tr></table></body></html>");
		
		return msgFooter.toString();
	}
	
	
	
	
	
	
	

}
