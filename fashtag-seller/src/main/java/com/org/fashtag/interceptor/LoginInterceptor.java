package com.org.fashtag.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.org.fashtag.common.AppConstants;
import com.org.fashtag.model.AdminUser;
import com.org.fashtag.model.Vendor;

public class LoginInterceptor extends HandlerInterceptorAdapter implements
		AppConstants {

	private static Logger logger = (Logger) Logger.getInstance(LoginInterceptor.class);

	
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		try {
			
			Vendor vendor = (Vendor) request.getSession()
					.getAttribute(LOGGED_IN_VENDOR);

			if (vendor == null) {
				response.sendRedirect("index.htm");
				return false;
			}

		} catch (Exception e) {
			response.sendRedirect("index.htm");
			return false;
		}

		return true;
	}

	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

}
