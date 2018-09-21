package com.org.fashtag.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.org.fashtag.AppDTO;
import com.org.fashtag.common.ResponseStatus;
import com.org.fashtag.context.AdminBeanConstants;
import com.org.fashtag.dao.CategoryDao;
import com.org.fashtag.dao.VendorDao;
import com.org.fashtag.dao.impl.VendorDaoImpl;
import com.org.fashtag.model.Category;
import com.org.fashtag.model.Vendor;
import com.org.fashtag.service.VendorService;
import com.org.fashtag.service.impl.BaseServiceImpl;
import com.org.fashtag.util.DateUtils;
import com.org.fashtag.util.PasswordEncryptor;

public class VendorServiceImpl extends BaseServiceImpl implements VendorService,AdminBeanConstants {

	
	private VendorDaoImpl vendorDao;


	public AppDTO addVendor(AppDTO appDTO) 
	{
		Map controllerMap=appDTO.getControllerMap();
		Vendor vendor=(Vendor)controllerMap.get("VENDOR");
		String password=PasswordEncryptor.encrypt(vendor.getPassword());
		vendor.setPassword(password);
		vendor.setDateAdded(DateUtils.getCurrentDate());
		vendor.setDateModified(DateUtils.getCurrentDate());
		((VendorDao) getDAOBean(VENDOR_DAO)).addVendor(vendor);
		appDTO.setInfoMessage(getResourceMessage("VENDOR_ADDED"));
		
		return appDTO;
	}
	
	
	
	

	
	
	public AppDTO listAllVendors(AppDTO appDTO)
	{
		appDTO=((VendorDao) getDAOBean(VENDOR_DAO)).listAllVendors(appDTO);
		return appDTO;
	}
	
	
	public Vendor getVendorById(Vendor vendor)
	{
		
		vendor=(Vendor)((VendorDao) getDAOBean(VENDOR_DAO)).
				findObjectByCondition
				(Vendor.class,new String[] 
				{"id"},
				new Object[] {vendor.getId()});
		
		return vendor;
	}

	
	
	
	
public AppDTO updateVendor(AppDTO appDTO) {
	try{
	Map controllerMap=appDTO.getControllerMap();
	Vendor vendor=(Vendor)controllerMap.get("VENDOR");
	String password=PasswordEncryptor.encrypt(vendor.getPassword());
	vendor.setPassword(password);
	vendor.setDateModified(DateUtils.getCurrentDate());
	((VendorDao) getDAOBean(VENDOR_DAO)).updateVendor(vendor);
	appDTO.setInfoMessage(getResourceMessage("VENDOR_UPDATED"));
	}catch(Exception e){e.printStackTrace();}
			return appDTO;
}





public AppDTO deleteVendor(AppDTO appDTO) {
	try
	{
		Map controllerMap=appDTO.getControllerMap();
		Vendor vendor=(Vendor)controllerMap.get("VENDOR");
		((VendorDao) getDAOBean(VENDOR_DAO)).deleteVendor(vendor);
		appDTO.setInfoMessage(getResourceMessage("VENDOR_DELETED"));
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



	/* getters and setters*/




public VendorDaoImpl getVendorDao() {
	return vendorDao;
}

public void setVendorDao(VendorDaoImpl vendorDao) {
	this.vendorDao = vendorDao;
}
	






	

}
