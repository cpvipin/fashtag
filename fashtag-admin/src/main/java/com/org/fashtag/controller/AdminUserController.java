package com.org.fashtag.controller;

import java.util.Collection;

import javax.servlet.http.HttpSession;

import org.omg.PortableInterceptor.USER_EXCEPTION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.org.fashtag.context.ApplicationContext;
import com.org.fashtag.controller.BaseController;
import com.org.fashtag.model.AdminUser;
import com.org.fashtag.model.Customer;
import com.org.fashtag.service.impl.AdminUserServiceImpl;
import com.org.fashtag.util.CommonUtils;
import com.org.fashtag.util.DateUtils;
import com.org.fashtag.util.PasswordEncryptor;



@Controller
public class AdminUserController extends BaseController {
	
	
	@Autowired
	private AdminUserServiceImpl adminUserService;

	

	@RequestMapping("/index")
	public ModelAndView index() {
		
		ModelAndView mv = new ModelAndView("index");
		return mv;
	
	
	}

	@RequestMapping("/login")
	public ModelAndView login(@ModelAttribute AdminUser adminUser) 
	{
		ModelAndView mv = new ModelAndView("redirect:/index.htm");
		try{
		adminUser=adminUserService.doAuthenticate(adminUser);
		if(adminUser==null)
		{

			setError_message(getResourceMessage("WRONG_USER_NAME_PASSWORD"), true);
		}
		else
		{
			getHttpSession().setAttribute(LOGGED_IN_USER, adminUser);
			validateSession(adminUser);
			
			
			mv.setViewName("redirect:/dashboard.htm");
		}
		}
		catch(Exception  e)
		{
			setError_message(getResourceMessage("WRONG_USER_NAME_PASSWORD")+e.getMessage(), true);
		}
		
		
		return mv;
	}
	


	@RequestMapping("/logout")
	public ModelAndView logout() {
		
		ModelAndView mv=new ModelAndView("redirect:/index.htm");
		getHttpSession().invalidate();
		
		return mv;
		}


	@RequestMapping("/dashboard")
	public ModelAndView dashboard() {
		
		ModelAndView mv=new ModelAndView();
		Collection ordersColl=adminUserService.getLatestOrders();
		mv.addObject("ordersList",ordersColl);
		
		
		mv.setViewName("dashboard");
		
		return mv;
		}
	
	
	
	
	/*getters and setters*/
	
	public AdminUserServiceImpl getAdminUserService() {
		return adminUserService;
	}

	public void setAdminUserService(AdminUserServiceImpl adminUserService) {
		this.adminUserService = adminUserService;
	}
	
	
	
	
	
	/*methods */
	
	private void validateSession(AdminUser adminUser) {
		try {
			HttpSession aSession = getHttpSession();
			HttpSession oldSess = (HttpSession) ApplicationContext.getActiveSessionMap().get(adminUser.getId());
			if (oldSess != null	&& !CommonUtils.nullSafeObjectEquals(oldSess.getId(),aSession.getId())) {
				oldSess.invalidate();
			}
			ApplicationContext.getActiveSessionMap().put(adminUser.getId(),aSession);
		} catch (Exception e) {
			
		}
	}
}
