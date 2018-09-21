package com.org.fashtag.dao.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;

import com.org.fashtag.AppDTO;
import com.org.fashtag.beans.Page;
import com.org.fashtag.context.ApplicationContext;
import com.org.fashtag.context.BeanConstants;
import com.org.fashtag.context.TransactionManager;
import com.org.fashtag.dao.ProductDao;
import com.org.fashtag.dao.impl.BaseDaoImpl;
import com.org.fashtag.hibernate.PaginationCriteria;
import com.org.fashtag.model.Category;
import com.org.fashtag.model.DesignAttribute;
import com.org.fashtag.model.DesignAttributeSpecification;
import com.org.fashtag.model.Product;
import com.org.fashtag.model.Size;
import com.org.fashtag.model.Vendor;
import com.org.fashtag.util.CommonUtils;
import com.org.fashtag.util.QueryUtils;



public class ProductDaoImpl extends BaseDaoImpl implements ProductDao {


	
	/* products */
	
	
		
	public void addProduct(Product product) {
		getHibernateTemplate().save(product);
		
	}







	public AppDTO listAllProductsOfVendor(AppDTO appDTO) {

		Session session=getSession();
		try{
			Page productPage = (Page) appDTO.getControllerMap().get("PAGE");
			String sortField = productPage.getOrderBy();
			String productName = (String) appDTO.getControllerMap().get("name");
			Vendor vendor = (Vendor) appDTO.getControllerMap().get("vendor");
			boolean activeStatus=(Boolean)appDTO.getControllerMap().get("approvalStatus");
			
			PaginationCriteria pCriteria = getPaginationCriteria(session,Product.class);
			ArrayList aliasArr = new ArrayList();
			
			
			if(!CommonUtils.isEmpty(productName))
			{
			pCriteria.add(Expression.like("name","%"+productName+"%"));
			}
			
			if (vendor != null) {
				pCriteria.add(Expression.eq("vendor.id", vendor.getId()));
			}
			
			pCriteria.add(Expression.eq("activeStatus", activeStatus));
			
			
			if (!CommonUtils.isEmpty(sortField)) {
				String[] tokens = sortField.split("-");
				String sortFld = tokens[tokens.length - 1];
				for (int i = 0; i < tokens.length - 1; i++) {
					if (!aliasArr.contains(tokens[i])) {
						if (i > 0) {
							pCriteria.createAlias(tokens[i - 1] + "."
									+ tokens[i], tokens[i]);
						} else {
							pCriteria.createAlias(tokens[i], tokens[i]);
						}
					}
					if (i == (tokens.length - 2)) {
						sortField = tokens[i] + "." + sortFld;
					}
				}
				if (productPage.getOrder() == PaginationCriteria.ORDER_ASC) {
					pCriteria.addOrder(Order.asc(sortField));
				} else {
					pCriteria.addOrder(Order.desc(sortField));
				}
			}
			productPage = pCriteria.createPage(productPage);
			appDTO.getControllerMap().put("PAGE", productPage);
			
			
		}
		catch(Exception e)
		{
			
		}
		return appDTO;
	}
	
	public Collection getproductDesignAttributes(int productId)
	{
		Collection designAttrList=((QueryUtils) ApplicationContext.getApplicationContext().
				getBean(BeanConstants.QUERYUTILS_DAO)).findByNamedQuery("prod.getDesignAttributesByProduct",
						new String[]{"productId"},
						new Object[]{ productId });
		
		return designAttrList;
	}




	@Override
	public void deleteProductImages(Product product) {
		Session session=TransactionManager.getInstance().getTxSession();
		session.createQuery(
					"delete from ProductImages where " + "product.id="
							+ product.getId()).executeUpdate();
	}

	@Override
	public void deleteProductSizes(Product product) {
		Session session=TransactionManager.getInstance().getTxSession();
		session.createQuery(
					"delete from ProductToSize where " + "product.id="
							+ product.getId()).executeUpdate();
	}

	
	
	@Override
	public void deleteSavedProductImages(int productId,int imageId) {
		Session session=getSession();
		session.createQuery(
					"delete from ProductImages where product.id="+ productId+" and id="+imageId).executeUpdate();

	}

	@Override
	public void deleteProductToDesignAttributeSpec(Product product) {
		Session session=TransactionManager.getInstance().getTxSession();
		session.createQuery(
					"delete from ProductToDesignAttributeSpecification where " + "product.id="
							+ product.getId()).executeUpdate();
	}


	public Collection getChildCategoryList(String likeKey)
	{

	
		Collection categoryColl=Collections.EMPTY_LIST;
	try	{
		
		categoryColl=((QueryUtils) ApplicationContext.getApplicationContext().
				getBean(BeanConstants.QUERYUTILS_DAO)).findByNamedQuery("ca.getChildCategories",
						new String[]{"name"},
						new Object[]{ likeKey });
		
	}catch(Exception e){ e.printStackTrace();}
		return categoryColl;
	}
	









}
