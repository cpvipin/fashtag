package com.org.fashtag.service.impl;

import java.util.Collection;
import java.util.Map;

import com.org.fashtag.AppDTO;
import com.org.fashtag.common.ResponseStatus;
import com.org.fashtag.context.AdminBeanConstants;
import com.org.fashtag.dao.ProductDao;
import com.org.fashtag.dao.SizeDao;
import com.org.fashtag.model.Size;
import com.org.fashtag.service.SizeService;
import com.org.fashtag.service.impl.BaseServiceImpl;
import com.org.fashtag.util.DateUtils;

public class SizeServiceImpl extends BaseServiceImpl implements SizeService,
		AdminBeanConstants {

	
	private SizeDao sizeDao;

	
	/* size services */
	
	public AppDTO addSize(AppDTO appDTO) {

		
		Map controllerMap=appDTO.getControllerMap();
		Size size=(Size)controllerMap.get("SIZE");
		size.setDateAdded(DateUtils.getCurrentDate());
		size.setDateModified(DateUtils.getCurrentDate());
		((SizeDao) getDAOBean(SIZE_DAO)).addSize(size);
		appDTO.setInfoMessage(getResourceMessage("SIZE_ADDED"));
		
		return appDTO;
	}
	
	
	

	
	public AppDTO updateSize(AppDTO appDTO) {
			try{

			Map controllerMap=appDTO.getControllerMap();
			Size size=(Size)controllerMap.get("SIZE");
			
			size.setDateModified(DateUtils.getCurrentDate());
					
			((SizeDao) getDAOBean(SIZE_DAO)).updateSize(size);
					
					appDTO.setInfoMessage(getResourceMessage("SIZE_UPDATED"));
			}catch(Exception e){e.printStackTrace();}
			
				return appDTO;
		
		
		
	}

	
	
	
	public AppDTO listAllSizes(AppDTO appDTO)
	{
		
		appDTO=((SizeDao) getDAOBean(SIZE_DAO)).listAllSizes(appDTO);
		return appDTO;
	}
	
	
	public Collection getAllActiveSize()
	{
		Collection activeSizeColl=((SizeDao) getDAOBean(SIZE_DAO)).findListByCondition(Size.class, 
				 new String[]{ "activeStatus"}
				, new Object[]{true});
		return activeSizeColl;
	}
	
	public Size getSizeById(int id)
	{
		Size size=(Size)((ProductDao) getDAOBean(PRODUCT_DAO)).
				findObjectByCondition
				(Size.class,new String[] 
				{"id"},
				new Object[] {id});
		
		return size;
	}


	
	

	public AppDTO deleteSize(AppDTO appDTO) {
		try
		{
			Map controllerMap=appDTO.getControllerMap();
			Size size=(Size)controllerMap.get("SIZE");
			((SizeDao) getDAOBean(SIZE_DAO)).deleteSize(size);
			appDTO.setInfoMessage(getResourceMessage("SIZE_DELETED"));
			appDTO.setResponseStatus(ResponseStatus.SUCCESS);
			
		}
		catch(Exception e)
		{
			appDTO.setErrorMessage(getResourceMessage("CHILD_RECORD_EXISTS"));
			appDTO.setResponseStatus(ResponseStatus.ERROR);
			return appDTO;
		}
		return appDTO;
	}





	public SizeDao getSizeDao() {
		return sizeDao;
	}





	public void setSizeDao(SizeDao sizeDao) {
		this.sizeDao = sizeDao;
	}
	
	
	

}
