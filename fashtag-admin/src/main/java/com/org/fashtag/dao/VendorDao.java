package com.org.fashtag.dao;

import java.util.Collection;

import com.org.fashtag.AppDTO;
import com.org.fashtag.dao.BaseDao;
import com.org.fashtag.model.Category;
import com.org.fashtag.model.Vendor;

public interface VendorDao extends BaseDao {
	
	public void addVendor(Vendor vendor);
	
	public AppDTO listAllVendors(AppDTO appDTO);
	
	public void updateVendor(Vendor vendor);
	
	public void deleteVendor(Vendor vendor);
	
	public Collection getVendorsByName(String likeKey);

	
	

}
