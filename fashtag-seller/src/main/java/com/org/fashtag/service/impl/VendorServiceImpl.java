package com.org.fashtag.service.impl;


import java.util.Collection;
import java.util.Collections;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.org.fashtag.context.VendorBeanConstants;
import com.org.fashtag.dao.VendorDao;
import com.org.fashtag.model.AdminUser;
import com.org.fashtag.model.Vendor;
import com.org.fashtag.service.VendorService;
import com.org.fashtag.service.impl.BaseServiceImpl;
import com.org.fashtag.util.PasswordEncryptor;

public class VendorServiceImpl extends BaseServiceImpl implements VendorService,VendorBeanConstants

{
	
	private VendorDao vendorDao;
	
public Vendor doAuthenticate(Vendor vendor) {
		
	Vendor ven = (Vendor) vendorDao
				.findObjectByCondition(
						Vendor.class,
						new String[] { "emailId", "password","activeStatus" },
						new Object[] { vendor.getEmailId(),PasswordEncryptor.encrypt(vendor.getPassword()),STATUS_ACTIVE });
		
		
		return ven;
	}



public VendorDao getVendorDao() {
		return vendorDao;
	}

	public void setVendorDao(VendorDao vendorDao) {
		this.vendorDao = vendorDao;
	}


}
