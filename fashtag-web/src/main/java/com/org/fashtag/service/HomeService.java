package com.org.fashtag.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.servlet.ModelAndView;

import com.org.fashtag.AppDTO;
import com.org.fashtag.service.BaseService;

public interface HomeService extends BaseService {
	
	public AppDTO loadHomeElements();
	
	public AppDTO getChildMenuList(AppDTO appDTO);
		
	public AppDTO addCareRequest(AppDTO appDTO);
	
	public void logAnalytics(String ipAddress);
	
	
}
