package com.org.fashtag.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import com.org.fashtag.AppDTO;
import com.org.fashtag.common.ResponseStatus;
import com.org.fashtag.context.AdminBeanConstants;
import com.org.fashtag.context.TransactionManager;
import com.org.fashtag.dao.CategoryDao;
import com.org.fashtag.dao.ProductDao;
import com.org.fashtag.dao.VendorDao;
import com.org.fashtag.dao.impl.ProductDaoImpl;
import com.org.fashtag.model.AdminUser;
import com.org.fashtag.model.Category;
import com.org.fashtag.model.DesignAttribute;
import com.org.fashtag.model.DesignAttributeSpecification;
import com.org.fashtag.model.Product;
import com.org.fashtag.model.ProductImages;
import com.org.fashtag.model.ProductToDesignAttributeSpecification;
import com.org.fashtag.model.ProductToSize;
import com.org.fashtag.model.Size;
import com.org.fashtag.service.CategoryService;
import com.org.fashtag.service.ProductService;
import com.org.fashtag.service.impl.BaseServiceImpl;
import com.org.fashtag.util.CommonUtils;
import com.org.fashtag.util.DateUtils;
import com.org.fashtag.util.PasswordEncryptor;



public class ProductServiceImpl extends BaseServiceImpl implements ProductService,AdminBeanConstants {

	
	private ProductDaoImpl productDao;

	
	
	/* product */
	
	
	public Collection getChildCategoryList(String searchKey)
	{
		Collection categoryList=new ArrayList();

		try{
		categoryList=((CategoryDao) getDAOBean(CATEGORY_DAO))
		.getChildCategoryList("%" +	searchKey+ "%");
		}
		catch(Exception e){
			e.printStackTrace();
			
		}
		
		return categoryList;
	}
	
	
	


	public Collection getVendorsByName(String searchKey) {
		
		Collection vendorList=new ArrayList();

		try{
			vendorList=((VendorDao) getDAOBean(VENDOR_DAO))
		.getVendorsByName("%"+searchKey+"%");
			
			
		}
		catch(Exception e){
			e.printStackTrace();
			
		}
		
		return vendorList;
	}

	
	
	
/* product */
	
	
	
	public AppDTO listAllProducts(AppDTO appDTO)
	{
		
		appDTO=((ProductDao) getDAOBean(PRODUCT_DAO)).listAllProduct(appDTO);		
			return appDTO;
		}

	
	/*
	 * 
	 * 
	 * (non-Javadoc)
	 * @see com.org.fashtag.service.ProductService#addProduct(com.org.fastag.AppDTO)
	 * This method add product and then add product to size , product images, product to desgn attributes
	 * design attribute specifications . Any of the step fails everything will be rollbacked
	 */
	
	public AppDTO addProduct(AppDTO appDTO) {
		Map controllerMap=appDTO.getControllerMap();
		
		Product product=(Product)controllerMap.get("PRODUCT");
		product.setDateAvailable(DateUtils.getDate((String)controllerMap.get("DATE_AVAILABLE")));
		String[] productSize=(String[]) controllerMap.get("PRODUCT_SIZE");
		String[] productImage=(String[]) controllerMap.get("PRODUCT_IMAGE");
		HttpServletRequest reqObj=(HttpServletRequest) controllerMap.get("REQUEST");
		product.setDateAdded(DateUtils.getCurrentDate());
		product.setDateModified(DateUtils.getCurrentDate());
		product.setTotalViews(0);
		
		TransactionManager.begin();
		TransactionManager.txCreate(product);

	
		
		if(!addProductSize(productSize,product))
		{ 
		TransactionManager.rollback();
		}
		
		if(!addProductImage(productImage,product))
		{ 
			TransactionManager.rollback();
		}
		if(!addDesignAttributeSpecification(reqObj,product))
		{
			TransactionManager.rollback();
		}
		
		TransactionManager.commit();
	
		appDTO.setInfoMessage(getResourceMessage("PRODUCT_ADDED"));
		return appDTO;
	}

	
	
	


	@Override
	public AppDTO updateProduct(AppDTO appDTO) {
		
		
		Map controllerMap=appDTO.getControllerMap();
		
		try{
		Product product=(Product)controllerMap.get("PRODUCT");
		
		product.setDateAvailable(DateUtils.getDate((String)controllerMap.get("DATE_AVAILABLE")));
		String[] productSize=(String[]) controllerMap.get("PRODUCT_SIZE");
		String[] productImage=(String[]) controllerMap.get("PRODUCT_IMAGE");
		HttpServletRequest reqObj=(HttpServletRequest) controllerMap.get("REQUEST");
		product.setDateModified(DateUtils.getCurrentDate());
		
		
		TransactionManager.begin();
		TransactionManager.txUpdate(product);

		TransactionManager.getInstance().getTxSession().flush();
		
		productDao.deleteProductImages(product);
		productDao.deleteProductSizes(product);
		productDao.deleteProductToDesignAttributeSpec(product);
		
		if(!addProductSize(productSize,product))
		{ 
		TransactionManager.rollback();
		}
		
		if(!addProductImage(productImage,product))
		{ 
			TransactionManager.rollback();
		}
		if(!addDesignAttributeSpecification(reqObj,product))
		{
			TransactionManager.rollback();
		}
		
		TransactionManager.commit();
	
		appDTO.setInfoMessage("Product updated succesully");
		
		
		}
		catch(Exception e)
		{
			TransactionManager.rollback();
			e.printStackTrace();
			appDTO.setResponseStatus(ResponseStatus.ERROR);
			appDTO.setErrorMessage("Unexpected Exception: Product cannot be added");
		}
		
		return appDTO;
	}



	
	
	private boolean addProductSize(String[] sizeArr,Product product) {
		
		for (String s : sizeArr) {
			try{
			int sizeId=Integer.parseInt(s);
			ProductToSize productToSize=new ProductToSize();
			productToSize.setProduct(product);
			
			
			Size size=(Size)((ProductDao) getDAOBean(PRODUCT_DAO)).
			findObjectByCondition
			(Size.class,new String[] 
			{"id"},
			new Object[] {sizeId});
			
			productToSize.setSize(size);
			TransactionManager.txCreate(productToSize);
			}
			catch(Exception e)
			{
				e.printStackTrace();
				return false;
			}
		}

		return true;
	}

	
	
private boolean addProductImage(String[] imageArr,Product product) {
		
		for (String s : imageArr) {
			try{
			ProductImages productImages=new ProductImages();
			productImages.setImage(s);			
			productImages.setProduct(product);
			productImages.setSortOrder(0);
			
			TransactionManager.txCreate(productImages);
			}
			catch(Exception e)
			{
				e.printStackTrace();
				return false;
			}
		}

		return true;
	}


private boolean addDesignAttributeSpecification(HttpServletRequest request,Product product) 
{
	try{
	int count=Integer.parseInt(request.getParameter("designAttributeCount"));
	for(int i=0;i<count;i++)
	{

		String[] specArr=request.getParameterValues("designAttrSpec_"+i);
		
		Integer isRecVal=0;
		if(!CommonUtils.isEmpty(request.getParameter("is_recommended_"+i))){
		isRecVal=Integer.parseInt(request.getParameter("is_recommended_"+i));
		}
			for(String spe: specArr)
			{
				ProductToDesignAttributeSpecification productToDesignAttSpec=new ProductToDesignAttributeSpecification();

				Integer specId=Integer.parseInt(spe);
				DesignAttributeSpecification designAttrSpec=(DesignAttributeSpecification)((ProductDao) getDAOBean(PRODUCT_DAO)).
				findObjectByCondition
				(DesignAttributeSpecification.class,
				new String[]{"id"},
				new Object[]{specId});
				productToDesignAttSpec.setDesignAttributeSpecification(designAttrSpec);
				productToDesignAttSpec.setProduct(product);
				if(isRecVal==specId) productToDesignAttSpec.setIsRecommended(true);
				
				TransactionManager.txCreate(productToDesignAttSpec);
				
			}
		
		
		
	}
	}
	catch(Exception e){
		e.printStackTrace(); 
		return false;}
	return true;
}




/*
 * 
 * (non-Javadoc)
 * @see com.org.fashtag.service.ProductService#deleteProduct(com.org.fashtag.AppDTO)
 * 
 * Delete only inactive Products
 * 
 * 
 */
	

public AppDTO deleteProduct(AppDTO appDTO)
{
	try{
		Map dataMap=appDTO.getControllerMap();
		Product product=(Product)dataMap.get("PRODUCT");
		
		product = (Product) ((ProductDao) getDAOBean(PRODUCT_DAO))
		.findObjectByCondition(Product.class, new String[] { "id",
				 },
				new Object[] { product.getId()});
		
		
		if(!product.getActiveStatus())
		{
		TransactionManager.begin();

		productDao.deleteProductImages(product);
		productDao.deleteProductSizes(product);
		productDao.deleteProductToDesignAttributeSpec(product);

		
		TransactionManager.txDelete(product);

		TransactionManager.commit();
		
		}
		else
		{
			appDTO.setErrorMessage("Active product cannot be deleted. First make it inactive then delete.");
			appDTO.setResponseStatus(ResponseStatus.ERROR);
		}
		
		
	}
	catch(Exception e)
	{
		e.printStackTrace();
		appDTO.setResponseStatus(ResponseStatus.ERROR);
		appDTO.setErrorMessage("Error: Cannot be deleted child record exists.");
	}
	
	return appDTO;
	
}






public AppDTO getProductDetails(AppDTO appDTO) {

		Map controllerMap = appDTO.getControllerMap();
		Product product = (Product) controllerMap.get("PRODUCT");
		
		product = (Product) ((ProductDao) getDAOBean(PRODUCT_DAO))
		.findObjectByCondition(Product.class, new String[] { "id"},
				new Object[] { product.getId() });

		
		
		Collection designAttributeList=productDao.getproductDesignAttributes(product.getId());
		
		
		
		

		appDTO.getControllerMap().put("PRODUCT", product);
		appDTO.getControllerMap().put("DESIGN_ATTR_LIST",designAttributeList);
		
	return appDTO;
}








	/* getters and setters*/
	
	public ProductDaoImpl getProductDao() {
		return productDao;
	}

	public void setProductDao(ProductDaoImpl productDao) {
		this.productDao = productDao;
	}






	









	

}
