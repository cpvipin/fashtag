package com.org.fashtag.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.org.fashtag.AppDTO;
import com.org.fashtag.beans.Page;
import com.org.fashtag.common.ResponseStatus;
import com.org.fashtag.controller.BaseController;
import com.org.fashtag.model.Category;
import com.org.fashtag.model.Vendor;
import com.org.fashtag.service.impl.VendorServiceImpl;

@Controller
public class VendorController extends BaseController {

	@Autowired
	private VendorServiceImpl vendorService;

	@RequestMapping("/addVendor")
	public ModelAndView addVendor() {
		ModelAndView mv = new ModelAndView("addVendor");
		
		return mv;
	}

	@RequestMapping("/saveVendor")
	public ModelAndView saveVendor(@ModelAttribute Vendor vendor) {
		ModelAndView mv = new ModelAndView();
		AppDTO appDTO = new AppDTO();
		Map controllerMap = new HashMap();
		try {
			controllerMap.put("VENDOR", vendor);
			appDTO.setControllerMap(controllerMap);
			appDTO = vendorService.addVendor(appDTO);

			if (appDTO.getResponseStatus() == ResponseStatus.ERROR) {
				setError_message(appDTO.getErrorMessage(), true);
				mv.setViewName("redirect:/addVendor.htm");
			}

			mv.setViewName("redirect:/listVendor.htm");
			setInfo_message(appDTO.getInfoMessage(), true);

		} catch (Exception e) {
			e.printStackTrace();
			setError_message(getResourceMessage("UNEXPECTED_ERROR"), true);
			mv.setViewName("redirect:/addVendor.htm");
		}
		return mv;
	}
	
	@RequestMapping("/editVendor")
	public ModelAndView editVendor(@ModelAttribute Vendor vendor) {

		ModelAndView mv = new ModelAndView("addVendor");
		vendor = vendorService.getVendorById(vendor);

		mv.addObject("vendor", vendor);
		return mv;

	}

	@RequestMapping("/updateVendor")
	public String updateVendor(@ModelAttribute Vendor vendor) {

		ModelAndView mv = new ModelAndView();
		AppDTO appDTO = new AppDTO();
		Map controllerMap = new HashMap();
		try {
			controllerMap.put("VENDOR", vendor);
			appDTO.setControllerMap(controllerMap);
			appDTO = vendorService.updateVendor(appDTO);
		} catch (Exception e) {
			e.printStackTrace();
			setError_message(getResourceMessage("UNEXPECTED_ERROR"), true);
			return "redirect:/addVendor.htm";
		}

		setInfo_message(appDTO.getInfoMessage(), true);

		return "redirect:/listVendor.htm?name="+vendor.getName();

	}

	

	@RequestMapping("/listVendor")
	public ModelAndView listVendor(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("listVendor");
		Page vendorPage = getPage();
		List vendorList = new ArrayList();
		vendorPage.setResultList(Collections.EMPTY_LIST);
		AppDTO appDTO = new AppDTO();
		Map controllerMap = new HashMap();
		controllerMap.put("PAGE", vendorPage);

		try {

			String name = (String) request.getParameter("name");
			controllerMap.put("name", name);
			appDTO.setControllerMap(controllerMap);
			appDTO = vendorService.listAllVendors(appDTO);
			vendorPage = (Page) appDTO.getControllerMap().get("PAGE");
			vendorList = vendorPage.getResultList();
			setPage(vendorPage);
			mv.addObject("vendorList", vendorList);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return mv;
	}

	
	@RequestMapping("/deleteVendor")
	public ModelAndView deleteVendor(@ModelAttribute Vendor vendor) {
		ModelAndView mv = new ModelAndView("redirect:/listVendor.htm");
		AppDTO appDTO = new AppDTO();
		Map controllerMap = new HashMap();
		controllerMap.put("VENDOR", vendor);
		appDTO.setControllerMap(controllerMap);
		appDTO = vendorService.deleteVendor(appDTO);
		if (appDTO.getResponseStatus() == ResponseStatus.ERROR) {
			setError_message(appDTO.getErrorMessage(), true);
			mv.setViewName("redirect:/listVendor.htm");
		}

		setInfo_message(appDTO.getInfoMessage(), true);
		return mv;
	}

	/* getters and setters */

	public VendorServiceImpl getVendorService() {
		return vendorService;
	}

	public void setVendorService(VendorServiceImpl vendorService) {
		this.vendorService = vendorService;
	}

}
