package com.org.fashtag.service.impl;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.org.fashtag.AppDTO;
import com.org.fashtag.common.Gender;
import com.org.fashtag.common.ResponseStatus;
import com.org.fashtag.context.TransactionManager;
import com.org.fashtag.context.VendorBeanConstants;
import com.org.fashtag.dao.ProductDao;
import com.org.fashtag.dao.impl.ProductDaoImpl;
import com.org.fashtag.model.DesignAttributeSpecification;
import com.org.fashtag.model.Product;
import com.org.fashtag.model.ProductImages;
import com.org.fashtag.model.ProductToDesignAttributeSpecification;
import com.org.fashtag.model.ProductToSize;
import com.org.fashtag.model.Size;
import com.org.fashtag.model.Vendor;
import com.org.fashtag.service.ProductService;
import com.org.fashtag.service.impl.BaseServiceImpl;
import com.org.fashtag.util.CommonUtils;
import com.org.fashtag.util.DateUtils;

public class ProductServiceImpl extends BaseServiceImpl implements ProductService,VendorBeanConstants
{
	

	private ProductDao productDao;

	public AppDTO listAllProductsOfVendor(AppDTO appDTO)
	{
		
		appDTO=productDao.listAllProductsOfVendor(appDTO);		
			return appDTO;
	}

	public Collection getAllActiveSize()
	{
		Collection activeSizeColl=productDao.findListByCondition(Size.class, 
				 new String[]{ "activeStatus"}
				, new Object[]{true});
		return activeSizeColl;
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
		
		Vendor vendor =(Vendor)getHttpSession().getAttribute(LOGGED_IN_VENDOR);
		Product product=(Product)controllerMap.get("PRODUCT");
		MultipartHttpServletRequest request=(MultipartHttpServletRequest)controllerMap.get("REQUEST");
		
		product.setActiveStatus(false);
		product.setDateAvailable(DateUtils.getDate((String)controllerMap.get("DATE_AVAILABLE")));
		product.setGender(Gender.FEMALE.getValue());
		product.setIsFeatured(false);
		product.setVendor(vendor);
		
		
		String[] productSize=(String[]) controllerMap.get("PRODUCT_SIZE");
		Collection productImage=(Collection) controllerMap.get("PRODUCT_IMAGE");

		product.setDateAdded(DateUtils.getCurrentDate());
		product.setDateModified(DateUtils.getCurrentDate());
		product.setTotalViews(0);
		
		TransactionManager.begin();
		TransactionManager.txCreate(product);

	
		
		if(!addProductSize(productSize,product))
		{ 
		TransactionManager.rollback();
		}
		
		if(!addProductImage(productImage,product,request))
		{ 
			TransactionManager.rollback();
		}
		if(!addDesignAttributeSpecification(request,product))
		{
			TransactionManager.rollback();
		}
		else
		{
		TransactionManager.commit();
		appDTO.setInfoMessage(getResourceMessage("PRODUCT_ADDED"));
		}
		return appDTO;
	}
	
	
	


	@Override
	public AppDTO updateProduct(AppDTO appDTO) {
		
		
		Map controllerMap=appDTO.getControllerMap();
		
		try{
		Product product=(Product)controllerMap.get("PRODUCT");
		Vendor vendor =(Vendor)getHttpSession().getAttribute(LOGGED_IN_VENDOR);
		
		product.setDateAvailable(DateUtils.getDate((String)controllerMap.get("DATE_AVAILABLE")));
		product.setActiveStatus(false);
		product.setGender(Gender.FEMALE.getValue());
		product.setIsFeatured(false);
		product.setVendor(vendor);
		
		
		
		String[] productSize=(String[]) controllerMap.get("PRODUCT_SIZE");
		Collection productImage=(Collection) controllerMap.get("PRODUCT_IMAGE");
		MultipartHttpServletRequest reqObj=(MultipartHttpServletRequest) controllerMap.get("REQUEST");
		product.setDateModified(DateUtils.getCurrentDate());
	
		TransactionManager.begin();
		TransactionManager.txUpdate(product);
		productDao.deleteProductSizes(product);
		productDao.deleteProductToDesignAttributeSpec(product);
		
		if(!addProductSize(productSize,product))
		{ 
		TransactionManager.rollback();
		}
		
		if(!addProductImage(productImage,product,reqObj))
		{ 
			TransactionManager.rollback();
		}
		if(!addDesignAttributeSpecification(reqObj,product))
		{
			TransactionManager.rollback();
			appDTO.setResponseStatus(ResponseStatus.ERROR);
			appDTO.setErrorMessage("Sorry , Product cannot be updated . Please contact support team .");
		}
	
		else
		{
		TransactionManager.commit();
		appDTO.setInfoMessage("Product updated succesully");
		}
		
		
		}
		catch(Exception e)
		{
			TransactionManager.rollback();
			e.printStackTrace();
			appDTO.setResponseStatus(ResponseStatus.ERROR);
			appDTO.setErrorMessage("Sorry , Product cannot be updated . Please contact support team .");
		}
		
		return appDTO;
	}



	
	

	private boolean addDesignAttributeSpecification(HttpServletRequest request,Product product) 
	{
		try{
		int count=Integer.parseInt(request.getParameter("designAttributeCount"));
		for(int i=0;i<count;i++)
		{

			String[] specArr=request.getParameterValues("designAttrSpec_"+i);
			
			
			if(specArr!=null)
			{
			Integer isRecVal=0;
			if(!CommonUtils.isEmpty(request.getParameter("is_recommended_"+i))){
			isRecVal=Integer.parseInt(request.getParameter("is_recommended_"+i));
			}
				for(String spe: specArr)
				{
					ProductToDesignAttributeSpecification productToDesignAttSpec=new ProductToDesignAttributeSpecification();

					Integer specId=Integer.parseInt(spe);
					DesignAttributeSpecification designAttrSpec=(DesignAttributeSpecification) productDao.
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
		}
		catch(Exception e){
			e.printStackTrace(); 
			return false;}
		return true;
	}
	

	private boolean addProductSize(String[] sizeArr,Product product) {
		
		for (String s : sizeArr) {
			try{
			int sizeId=Integer.parseInt(s);
			ProductToSize productToSize=new ProductToSize();
			productToSize.setProduct(product);
			
			
			Size size=(Size)productDao.
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

	
	
	private boolean addProductImage(Collection imageArr,Product product,MultipartHttpServletRequest request) {
		String imgDir=getImageDir();
		
		try{
			
	
			/* upload default image first */
				
		
			MultipartFile mDFile=request.getFile("defaultFileImage");
			if(!CommonUtils.isEmpty(mDFile.getOriginalFilename()))
			{
			String defImageName=product.getId()+"_thumb_"+System.currentTimeMillis()+"."+FilenameUtils.getExtension(mDFile.getOriginalFilename());	
			File defaultFile=new File(imgDir+defImageName);
			defaultFile.setExecutable(true,false);
			byte[] defbytes;
			defbytes=mDFile.getBytes();
			BufferedOutputStream buffDefStream =new BufferedOutputStream(new FileOutputStream(defaultFile));
			buffDefStream.write(defbytes);
			buffDefStream.close();
			
			product.setDefaultImage(defImageName);
			TransactionManager.txUpdate(product);
			}
			
			
			Iterator iter=imageArr.iterator();
			int iterCount=0;
			while(iter.hasNext())
			{
				iterCount++;
				MultipartFile mFile=(MultipartFile)iter.next();
				String newImageName=product.getId()+"_"+iterCount+"_"+System.currentTimeMillis()+"."+FilenameUtils.getExtension(mFile.getOriginalFilename());
				File file=new File(imgDir+newImageName);
				file.setExecutable(true,false);
				byte[] bytes;
				bytes=mFile.getBytes();
				BufferedOutputStream buffStream =new BufferedOutputStream(new FileOutputStream(file));
				buffStream.write(bytes);
				buffStream.close();
				
				
				
			ProductImages productImages=new ProductImages();
			productImages.setImage(newImageName);			
			productImages.setProduct(product);
			productImages.setSortOrder(0);
			
			TransactionManager.txCreate(productImages);
			}
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}

		return true;
	}
	
	
	public AppDTO getProductDetails(AppDTO appDTO) {

		Map controllerMap = appDTO.getControllerMap();
		Product product = (Product) controllerMap.get("PRODUCT");
		
		product = (Product) productDao.findObjectByCondition(Product.class, new String[] { "id"},
				new Object[] { product.getId() });
		Collection designAttributeList=productDao.getproductDesignAttributes(product.getId());
		
		appDTO.getControllerMap().put("DESIGN_ATTR_LIST",designAttributeList);
		appDTO.getControllerMap().put("PRODUCT", product);
	return appDTO;
}


	public AppDTO removeSavedProductImage(AppDTO appDTO)
	{
		
		Vendor vendor =(Vendor)getHttpSession().getAttribute(LOGGED_IN_VENDOR);
		Map dataMap=appDTO.getControllerMap();
		int productId=Integer.parseInt((String)dataMap.get("productId"));
		int imageId=Integer.parseInt((String)dataMap.get("imageId"));
		
		Product product = (Product) productDao.findObjectByCondition(Product.class, new String[] { "id"},
				new Object[] { productId });
		if(product.getVendor().getId().intValue()==vendor.getId().intValue())
		{
			productDao.deleteSavedProductImages(productId, imageId);
			
			ProductImages productImg = (ProductImages) productDao.findObjectByCondition(ProductImages.class, new String[] { "id"},
					new Object[] { imageId });
			File file = new File(getAppSystemProperties(PRODUCT_IMAGE_DIR)+productImg.getImage());
			file.delete();

		}
		
		appDTO.setResponseStatus(ResponseStatus.SUCCESS);
		return appDTO;

	}
	

	public Collection getChildCategoryList(String searchKey)
	{
		Collection categoryList=new ArrayList();

		try{
		categoryList=productDao
		.getChildCategoryList("%" +	searchKey+ "%");
		}
		catch(Exception e){
			e.printStackTrace();
			
		}
		
		return categoryList;
	}
	


	public AppDTO deleteProduct(AppDTO appDTO)
	{
		try{
			Map dataMap=appDTO.getControllerMap();
			Product product=(Product)dataMap.get("PRODUCT");
			
			product = (Product) productDao
			.findObjectByCondition(Product.class, new String[] { "id",
					 },
					new Object[] { product.getId()});
			
			
			TransactionManager.begin();
			productDao.deleteProductImages(product);
			productDao.deleteProductSizes(product);
			TransactionManager.txDelete(product);
			TransactionManager.commit();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			appDTO.setResponseStatus(ResponseStatus.ERROR);
			appDTO.setErrorMessage("Error: Product cannot delete. Please contact support team");
		}
		
		return appDTO;
		
	}

	public AppDTO productSoldOut(AppDTO appDTO)
	{
		try{
			Map dataMap=appDTO.getControllerMap();
			Product product=(Product)dataMap.get("PRODUCT");
			product = (Product) productDao.findObjectByCondition(Product.class, new String[] { "id",},
					new Object[] { product.getId()});
			product.setQuantity(0);
			productDao.update(product);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			appDTO.setResponseStatus(ResponseStatus.ERROR);
			appDTO.setErrorMessage("Product marked as soldout. You can add quantity later for making the product active again.");
		}
		
		return appDTO;
	}
	
	
	
	private String getImageDir()
	{
		String imgDir=getAppSystemProperties(PRODUCT_IMAGE_DIR);
		return imgDir; 
	}

	public ProductDao getProductDao() {
		return productDao;
	}

	public void setProductDao(ProductDao productDao) {
		this.productDao = productDao;
	}
}
