package com.org.fashtag.controller;

import java.util.Collection;
import java.util.Collections;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.org.fashtag.context.ApplicationContext;
import com.org.fashtag.controller.BaseController;
import com.org.fashtag.model.AdminUser;
import com.org.fashtag.model.Customer;
import com.org.fashtag.model.Vendor;
import com.org.fashtag.service.VendorService;
import com.org.fashtag.service.impl.VendorServiceImpl;
import com.org.fashtag.util.CommonUtils;
import com.org.fashtag.util.DateUtils;
import com.org.fashtag.util.PasswordEncryptor;



@Controller
public class VendorController extends BaseController {
	
	
	@Autowired
	private VendorService vendorService;

	

	@RequestMapping("/index")
	public ModelAndView index() {
		
		ModelAndView mv = new ModelAndView("index");
		return mv;
	
	
		
	}
	
	@RequestMapping("/login")
	public ModelAndView login(@ModelAttribute Vendor vendor) 
	{
		ModelAndView mv = new ModelAndView("redirect:/index.htm");
		try{
			vendor=vendorService.doAuthenticate(vendor);
		if(vendor==null)
		{

			setError_message(getResourceMessage("WRONG_USER_NAME_PASSWORD"), true);
		}
		else
		{
			getHttpSession().setAttribute(LOGGED_IN_VENDOR, vendor);
			validateSession(vendor);
			
			
			mv.setViewName("redirect:/dashboard.htm");
		}
		}
		catch(Exception  e)
		{
			setError_message(getResourceMessage("WRONG_USER_NAME_PASSWORD")+e.getMessage(), true);
		}
		
		
		return mv;
	}
	
	

	@RequestMapping("/dashboard")
	public ModelAndView dashboard() {
		
		ModelAndView mv=new ModelAndView();
		Collection ordersColl=Collections.EMPTY_LIST;
		
		mv.addObject("ordersList",ordersColl);
		
		
		mv.setViewName("dashboard");
		
		return mv;
		}
	
	@RequestMapping("/logout")
	public ModelAndView logout() {
		
		ModelAndView mv=new ModelAndView("redirect:/index.htm");
		getHttpSession().invalidate();
		
		return mv;
		}
	

	private void validateSession(Vendor vendor) {
		try {
			HttpSession aSession = getHttpSession();
			HttpSession oldSess = (HttpSession) ApplicationContext.getActiveSessionMap().get(vendor.getId());
			if (oldSess != null	&& !CommonUtils.nullSafeObjectEquals(oldSess.getId(),aSession.getId())) {
				oldSess.invalidate();
			}
			ApplicationContext.getActiveSessionMap().put(vendor.getId(),aSession);
		} catch (Exception e) {
			
		}
	}



	public VendorService getVendorService() {
		return vendorService;
	}



	public void setVendorService(VendorService vendorService) {
		this.vendorService = vendorService;
	}

}
