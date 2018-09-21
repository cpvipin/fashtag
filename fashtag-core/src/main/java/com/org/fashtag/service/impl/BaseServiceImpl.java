package com.org.fashtag.service.impl;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.org.fashtag.beans.Page;
import com.org.fashtag.common.AppConstants;
import com.org.fashtag.context.ApplicationContext;
import com.org.fashtag.context.BeanConstants;
import com.org.fashtag.controller.BaseController;
import com.org.fashtag.service.BaseService;

public class BaseServiceImpl implements BaseService,BeanConstants,AppConstants {
	
	private static ResourceBundle resourceBundle;
	
	
	protected HttpSession getHttpSession() 
	{
		
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession();
		
		
		
		return session;
	}
		
	public Object getDAOBean(String beanId) 
	{
		return getApplicationContext().getBean(beanId);
	}
	
	/**
	 * @return Returns the applicationContext.
	 */
	public static ApplicationContext getApplicationContext() 
	{
		
			
		return ApplicationContext.getApplicationContext();
	}
	
	protected String getResourceMessage(String messageKey) {
		return getDTOMessage().getString(messageKey);
	}
	
	protected String getAppProperties(String key)
	{
		return BaseController.getAppProperties(key);
	}
	

	protected String getAppSystemProperties(String key)
	{
		return BaseController.getAppSystemProperties(key);
	}
	
	
	//TODO
	/* for multi language support get session bundle*/
	public ResourceBundle getDTOMessage() {
			resourceBundle = ResourceBundle.getBundle("messages", Locale.ENGLISH);
			return resourceBundle;
	}
	
	
}
