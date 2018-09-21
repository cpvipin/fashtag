package com.org.fashtag.service.impl;

import java.util.Collection;
import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.org.fashtag.context.AdminBeanConstants;
import com.org.fashtag.dao.AdminUserDao;
import com.org.fashtag.dao.BaseDao;
import com.org.fashtag.dao.OrdersDao;
import com.org.fashtag.dao.ProductDao;
import com.org.fashtag.dao.impl.AdminUserDaoImpl;
import com.org.fashtag.model.AdminUser;
import com.org.fashtag.model.Customer;
import com.org.fashtag.model.DesignAttribute;
import com.org.fashtag.model.OrdersStatus;
import com.org.fashtag.model.Product;
import com.org.fashtag.model.ProductToSize;
import com.org.fashtag.model.Size;
import com.org.fashtag.model.Vendor;
import com.org.fashtag.service.AdminUserService;
import com.org.fashtag.service.impl.BaseServiceImpl;
import com.org.fashtag.util.DateUtils;
import com.org.fashtag.util.PasswordEncryptor;

public class AdminUserServiceImpl extends BaseServiceImpl implements AdminUserService,AdminBeanConstants

{
	
	private AdminUserDaoImpl adminUserDao;
	
	private OrdersDao ordersDao;
	
	

	public AdminUser doAuthenticate(AdminUser adminUser) {
		
		AdminUser ad = (AdminUser) ((AdminUserDao) getDAOBean(ADMIN_USER_DAO))
				.findObjectByCondition(
						AdminUser.class,
						new String[] { "username", "password","activeStatus" },
						new Object[] { adminUser.getUsername(),PasswordEncryptor.encrypt(adminUser.getPassword()),STATUS_ACTIVE });
		
		
		/*OrdersStatus ordersstat=new OrdersStatus();
		ordersstat.setName("nam");
		ordersstat.setActiveStatus(true);
		
		((AdminUserDao) getDAOBean(ADMIN_USER_DAO)).add(ordersstat);*/
		
		
		if(ad!=null)
		{
			Date currentDate=DateUtils.getCurrentDate();
			ad.setLastLogin(currentDate);
			((AdminUserDao) getDAOBean(ADMIN_USER_DAO)).update(ad);	
		}
		
		return ad;
	}
	
	
	public Collection getLatestOrders(){
		
		Collection ordersColl=ordersDao.getLatestOrders();
		return ordersColl;
	}
	
	/* getters and setters*/
	
	
	public AdminUserDaoImpl getAdminUserDao() {
		return adminUserDao;
	}


	public void setAdminUserDao(AdminUserDaoImpl adminUserDao) {
		this.adminUserDao = adminUserDao;
	}


	public OrdersDao getOrdersDao() {
		return ordersDao;
	}


	public void setOrdersDao(OrdersDao ordersDao) {
		this.ordersDao = ordersDao;
	}


}
