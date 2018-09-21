package com.org.fashtag.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.servlet.ModelAndView;

import com.org.fashtag.AppDTO;
import com.org.fashtag.service.BaseService;

public interface OrdersService extends BaseService {
	
	public AppDTO trackOrder(AppDTO appDTO);
	
}
