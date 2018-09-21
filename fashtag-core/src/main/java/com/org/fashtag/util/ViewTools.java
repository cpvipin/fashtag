package com.org.fashtag.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collections;

import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.org.fashtag.common.AppConstants;
import com.org.fashtag.common.AppCoreConstants;
import com.org.fashtag.context.ApplicationContext;
import com.org.fashtag.context.BeanConstants;
import com.org.fashtag.controller.BaseController;
import com.org.fashtag.dao.CommonDao;
import com.org.fashtag.model.AdminUser;
import com.org.fashtag.model.Category;
import com.org.fashtag.model.Customer;
import com.org.fashtag.model.Vendor;


public class ViewTools implements BeanConstants,AppConstants {
	
	
	private static final SimpleDateFormat simpleDF = new SimpleDateFormat("dd/MM/yyyy");
	
	
	
	public static String getSubStr(String aString, int length)
	{
		
		if(!CommonUtils.isEmpty(aString) && (aString.length()>length))
		{
			return  aString.substring(0, length)+"..";
		}
		
		return aString;
	}

	public static String formatDate(Object aDate) {
		String dt = "";
		if (aDate != null) {
			try {
				dt = simpleDF.format(aDate);
			} catch (Exception e) {
				// DO nothing
			}
		}
		return dt;
	}
	
	public static String getCategoryParents(int catId)
	{
		StringBuffer parentStr=new StringBuffer();
		boolean isChild=false;
		int j=0;
		do
		{
			Category catObj=(Category) ((CommonDao) ApplicationContext.
				getApplicationContext().getBean(BeanConstants.COMMON_DAO)).
				findObjectByCondition(Category.class,
				new String[] { "id" },
				new Object[] {catId});
			
			if(catObj.getParent()!=null){
			catId=catObj.getParent().getId();
			parentStr.insert(0," > ");
			parentStr.insert(0,catObj.getParent().getName());
			isChild=true;
			}
			else{ isChild=false; }
		}
		while(isChild);
	
		return parentStr.toString();
	}
	
	
	public static String getBreadCrumb(int catId)
	{
		StringBuffer breadCrumbPath=new StringBuffer();
		boolean isChild=false;
		try{
		do
		{
			Category catObj=(Category) ((CommonDao) ApplicationContext.
				getApplicationContext().getBean(BeanConstants.COMMON_DAO)).
				findObjectByCondition(Category.class,
				new String[] { "id" },
				new Object[] {catId});
			String goTo="javascript:void(0)";
			if(catObj.getParent()!=null){ goTo="listProducts.htm?catId="+catObj.getId();}

			breadCrumbPath.insert(0,"<li><a href='"+goTo+"'>"+catObj.getName()+"</a></li>");

			
			if(catObj.getParent()!=null){
			catId=catObj.getParent().getId();
			isChild=true;
			}
			else{ isChild=false; }
		}
		while(isChild);
	}
	catch(Exception e){e.printStackTrace(); }
	
		breadCrumbPath.insert(0,"<li><a href='index.htm'>Home</a></li>");
		return breadCrumbPath.toString();
	}
	
	
	private static Object getDAOBean(String beanId) {
		return ApplicationContext.getApplicationContext().getBean(beanId);
	}
	
	public static String getLoggedInUserName()
	{
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession();
		AdminUser adminUser=(AdminUser)session.getAttribute(LOGGED_IN_USER);
		
		return adminUser.getUsername();
	}
	
	
	public static String getLoggedInVendorName()
	{
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession();
		Vendor vendor=(Vendor)session.getAttribute(LOGGED_IN_VENDOR);
		
		return vendor.getName();
	}


	public static String getLoggedInCustomerName()
	{
		String custName="";
		try{
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession();
		Customer customer=(Customer)session.getAttribute(LOGGED_IN_CUSTOMER);
		custName=customer.getFullName();
		}
		catch(Exception e)
		{
			custName="";
		}
		return custName;
	}
	
	public static Collection getLoggedInCustomerProfiles()
	{
		Collection customerProfiles=Collections.EMPTY_LIST;
		customerProfiles=BaseController.getLoggedInCustomerProfiles();
		
		return customerProfiles;
	}
	
	public boolean isSelectedDesign(String SelDesigns,int desSpecId)
	{
		String[] selDesArr=SelDesigns.split(",");
		for(int i=0;i<selDesArr.length;i++)
		{
			if(!CommonUtils.isEmpty(selDesArr[i]) && (Integer.parseInt(selDesArr[i])==desSpecId) )
			{
				return true;
				
				
			}
		}
		
		return false;
	}
	
	
	public int getCollectionSize(Collection aColl)
	{
		
		return aColl.size();
		
		
	}
	
	
	public BigDecimal getOffPercentage(BigDecimal price,BigDecimal offerPrice)
	{
		BigDecimal offerPer=new BigDecimal(0);
		try
		{
		offerPer=(new BigDecimal(100)).subtract(offerPrice.multiply(new BigDecimal(100)).divide(price,0,RoundingMode.HALF_UP));
		}
	catch(Exception e)
	{
		System.out.println("message"+e.getMessage());
		e.printStackTrace();
	}
	    return offerPer;
		
		
	}
	
	public static String getProdImagePath()
	{
		return BaseController.getAppSystemProperties(AppConstants.PRODUCT_IMAGE_DIR);
	}
	
	public static String getProdImageUrl()
	{
	 return BaseController.getAppSystemProperties(AppConstants.PRODUCT_IMAGE_URL);
	}
	public static String getDesAttrImageUrl()
	{
	 return BaseController.getAppSystemProperties(AppConstants.DES_ATTR_IMAGE_URL);
	}
	
	public static String getSupportPhone()
	{
	 return BaseController.getAppSystemProperties(AppConstants.SUPPORT_PHONE);
	}
	
	public static String getSupportEmail()
	{
	 return BaseController.getAppSystemProperties(AppConstants.SUPPORT_EMAIL);
	}
	
}
