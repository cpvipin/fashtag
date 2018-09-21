package com.org.fashtag.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.servlet.ModelAndView;

import com.org.fashtag.AppDTO;
import com.org.fashtag.service.BaseService;

public interface PaymentService  extends BaseService {
	
	public AppDTO initializePayment(AppDTO appDTO);
	
	public AppDTO finalizePayment(AppDTO appDTO);
}
