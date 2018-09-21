package com.org.fashtag.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.org.fashtag.AppDTO;
import com.org.fashtag.common.ResponseStatus;
import com.org.fashtag.controller.BaseController;
import com.org.fashtag.dao.BaseDao;
import com.org.fashtag.model.AdminUser;
import com.org.fashtag.model.Category;
import com.org.fashtag.model.Customer;
import com.org.fashtag.service.HomeService;

@Controller
public class HomeController extends BaseController {
	
	@Autowired
	private HomeService homeService;
	
	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("index");
		try
		{
			String ipAddress=request.getRemoteAddr();
			AppDTO appDTO=homeService.loadHomeElements();
				homeService.logAnalytics(ipAddress);
				
				Map dataMap=appDTO.getControllerMap();
				mv.addAllObjects(dataMap);
		}
		catch(Exception e)
		{
			e.printStackTrace();	
		}
		return mv;
	}
	
	
	/*
	 * Expect url for feedback contact and complaints
	 * req will have values("contact","feedback","complaint")
	 */
	@RequestMapping("/care")
	public ModelAndView care(HttpServletRequest req) {
	
		ModelAndView mv=new ModelAndView("care");
		AppDTO appDTO=new AppDTO();
		Map dataMap=new HashMap();
		try
		{
			String page=req.getParameter("page");
			Customer cust=(Customer)getHttpSession().getAttribute(LOGGED_IN_CUSTOMER);
				
		
			
			if(page.equalsIgnoreCase("submit"))
			{
				String commonField=req.getParameter("commonField");
				String comment=req.getParameter("comment");
				page=req.getParameter("careReq");
				
				dataMap.put("commonField",commonField);
				dataMap.put("comment",comment);
				dataMap.put("page",page);
				
				
				appDTO.setControllerMap(dataMap);
				appDTO=homeService.addCareRequest(appDTO);
				
				if(appDTO.getResponseStatus()==ResponseStatus.SUCCESS)
				{
					setInfo_message(appDTO.getInfoMessage(), true);
					mv.setViewName("redirect:/index.htm");
				}
				else
				{
					setError_message(appDTO.getErrorMessage(), true);
					mv.setViewName("redirect:/index.htm");

				}
				
				
			}
			else
			{
				if(cust==null)
				{
					setError_message(getResourceMessage(PLEASE_LOGIN), false);
				}
			mv.addObject("careReq",page);			
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return mv;
		
		
	}
	
	
	@RequestMapping("/getChildMenu")
	public @ResponseBody String getChildMenu(HttpServletRequest request) {
		JSONObject respObj = new JSONObject();
		try{
		String parentId=request.getParameter("menuId");
		AppDTO appDTO=new AppDTO();
		Map dataMap=new HashMap();
		dataMap.put("PARENT_ID", parentId);
		appDTO.setControllerMap(dataMap);
		appDTO=homeService.getChildMenuList(appDTO);
		dataMap=appDTO.getControllerMap();
		Collection menuList=(Collection) dataMap.get("childMenuList");
		Iterator iter=menuList.iterator();
		
		JSONArray level1Arr=new JSONArray();
		int prevLevel1Id=0;
		
		
		while(iter.hasNext())
		{
			JSONObject level1Obj=new JSONObject();
			Object[] menuArr=(Object[]) iter.next();
			String level1Name=(String) menuArr[0];
			Integer level1Id=(Integer) menuArr[1];
			if(prevLevel1Id!=level1Id)
			{
				level1Obj.put("id", level1Id);
				level1Obj.put("name", level1Name);
				JSONArray level2Array=getLevel2Menu(menuList,level1Id);
				level1Obj.put("level2", level2Array);
				level1Arr.put(level1Obj);
				prevLevel1Id=level1Id;

			}
			

			
		}
		respObj.put("level1",level1Arr);
		}
		catch(Exception e)
		{ 
			e.printStackTrace();
		}
		
		return respObj.toString();
		
	}
	
	
	
	private JSONArray getLevel2Menu(Collection childMenuList,int level1IdNeedle)
	{
		JSONArray jsonArray=new JSONArray();
		Iterator iter=childMenuList.iterator();
	
		while(iter.hasNext())
		{
			Object[] menuArr=(Object[]) iter.next();
			String level1Name=(String) menuArr[0];
			Integer level1Id=(Integer) menuArr[1];
			String level2Name=(String) menuArr[2];
			Integer level2Id=(Integer) menuArr[3];
			JSONObject jsonObject=new JSONObject();
			
			if(level1IdNeedle==level1Id)
			{
				try {
					jsonObject.put("name",level2Name);
					jsonObject.put("id",level2Id);

				} catch (JSONException e) {
					e.printStackTrace();
				}
				
				jsonArray.put(jsonObject);	
			}
			
			
			
		}
		
		return jsonArray;

		
	}
	
	
	@RequestMapping("/terms")
	public ModelAndView terms() {
		ModelAndView mv = new ModelAndView("terms");
		return mv;
	}
	

	@RequestMapping("/salesTerms")
	public ModelAndView salesTerms() {
		ModelAndView mv = new ModelAndView("salesTerms");
		return mv;
	}
	
	
	
	@RequestMapping("/privacy")
	public ModelAndView privacy() {
		ModelAndView mv = new ModelAndView("privacy");
		return mv;
	}
	
/* getters and setters */

	public HomeService getHomeService() {
		return homeService;
	}


	public void setHomeService(HomeService homeService) {
		this.homeService = homeService;
	}
	
}
