package com.org.fashtag.dao;

import com.org.fashtag.dao.BaseDao;
import com.org.fashtag.model.AdminUser;
import com.org.fashtag.model.Vendor;

public interface VendorDao extends BaseDao {

	public Vendor getVendorrById();

}
