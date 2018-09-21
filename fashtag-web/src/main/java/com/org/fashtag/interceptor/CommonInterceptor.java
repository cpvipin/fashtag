package com.org.fashtag.interceptor;

import java.math.BigDecimal;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.org.fashtag.common.AppConstants;
import com.org.fashtag.dao.CategoryDao;
import com.org.fashtag.model.AdminUser;
import com.org.fashtag.model.CustomerProfile;
import com.org.fashtag.util.CommonUtils;
public class CommonInterceptor extends HandlerInterceptorAdapter implements AppConstants {

	@Autowired
	private CategoryDao categoryDao;
	
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		return true;
	}

	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

		try{
		Collection categoryMenu=categoryDao.getParentCategories(true);
		modelAndView.addObject("parentCategoryList",categoryMenu);
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession();
		CustomerProfile custProf=(CustomerProfile)session.getAttribute(LOGGED_IN_CUSTOMER_PROFILE);
		String sessCart=(String)session.getAttribute(SESSION_CART);
		int productCount=0;
		BigDecimal totalPrice=new BigDecimal("0.00");
		JSONObject cartObj=new JSONObject();
		
		if(!CommonUtils.isEmpty(sessCart))
		{	
			cartObj=new JSONObject(sessCart); 
		productCount=(Integer)cartObj.get("count");	
		totalPrice=new BigDecimal(""+cartObj.get("totalPrice"));
		}
		
		modelAndView.addObject("productCount",productCount);
		modelAndView.addObject("totalPrice",totalPrice);
		modelAndView.addObject("profile",custProf);
		
		
		}
		catch(Exception e )
		{
			
		}


	}

	
	/* getters and setters */

	public CategoryDao getCategoryDao() {
		return categoryDao;
	}

	public void setCategoryDao(CategoryDao categoryDao) {
		this.categoryDao = categoryDao;
	}

	
	
	
}
