package com.org.fashtag.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.servlet.ModelAndView;

import com.org.fashtag.AppDTO;
import com.org.fashtag.service.BaseService;

public interface AuthenticateService  extends BaseService {
	
	public AppDTO signin(AppDTO appDTO);
	
	public AppDTO signup(AppDTO appDTO);

	public AppDTO setUserPassword(AppDTO appDTO);

	public AppDTO updatePassword(AppDTO appDTO);
	
	public AppDTO getCustomerSizeAttributes(AppDTO appDTO);
	
	public AppDTO addCustomerRequiredSize(AppDTO appDTO);
	
	public AppDTO forgotPassword(AppDTO appDTO);
	
	public AppDTO setSessCustomerProfile(AppDTO appDTO);
	
}
