package com.org.fashtag.dao;

import com.org.fashtag.dao.BaseDao;
import com.org.fashtag.model.AdminUser;

public interface AdminUserDao extends BaseDao {

	public AdminUser getUserByUserId();

}
