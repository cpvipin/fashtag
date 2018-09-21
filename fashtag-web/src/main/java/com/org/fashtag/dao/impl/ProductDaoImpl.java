package com.org.fashtag.dao.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.org.fashtag.AppDTO;
import com.org.fashtag.common.ResponseStatus;
import com.org.fashtag.context.ApplicationContext;
import com.org.fashtag.context.BeanConstants;
import com.org.fashtag.context.TransactionManager;
import com.org.fashtag.dao.CategoryDao;
import com.org.fashtag.dao.CustomerDao;
import com.org.fashtag.dao.ProductDao;
import com.org.fashtag.dao.impl.BaseDaoImpl;
import com.org.fashtag.model.BodyMeasurementAttribute;
import com.org.fashtag.model.Customer;
import com.org.fashtag.model.CustomerBodyMeasurement;
import com.org.fashtag.model.CustomerProfile;
import com.org.fashtag.model.DesignAttributeSpecification;
import com.org.fashtag.model.Product;
import com.org.fashtag.model.ProductToSize;
import com.org.fashtag.model.ReturnStatus;
import com.org.fashtag.model.Returns;
import com.org.fashtag.model.Size;
import com.org.fashtag.util.CommonUtils;
import com.org.fashtag.util.DateUtils;
import com.org.fashtag.util.QueryUtils;
import com.org.fashtag.util.UniqueResultHibCallback;

public class ProductDaoImpl extends BaseDaoImpl implements ProductDao {
	

public Collection getProductListByLayout(String layoutName)
{
	
	String layout=layoutName;
	SQLQuery query=null;
	Collection prodList=Collections.EMPTY_LIST;
	Date currentDate=DateUtils.getCurrentDate();

	try{
		String sql=
		"select p.name,p.id,p.default_image,p.offer_price,p.actual_price,p.category_id,p.total_likes,p.is_featured from "+
	"(SELECT "+
	 " t.name,t.id,t.actual_price,t.offer_price,t.category_id,t.default_image,t.date_modified,t.total_likes,lp.layout_name, ca.name category_name,t.is_featured, "+
	  "@cur/*'*/:=/*'*/ IF(t.category_id=@cat_id, @cur+1, 1) AS RowNumber, "+
	  "@cat_id /*'*/:=/*'*/ t.category_id "+
	"FROM "+
	"product t CROSS JOIN "+
	"(SELECT @cat_id /*'*/:=/*'*/(SELECT MIN(p.category_id) FROM product p), @cur/*'*/:=/*'*/0) AS init "+
	"join layout_preference lp on lp.category_id=t.category_id and lp.active_status=1 "+
	"join category ca on ca.id=t.category_id "+
	"where t.date_available<'"+currentDate+"'  and t.active_status=1 and t.quantity>0 "+
	"ORDER BY "+
	"t.category_id ASC,t.date_modified DESC) p "+
	"where RowNumber < 10 or p.is_featured=1 order by p.category_id ASC,p.is_featured DESC, p.date_modified DESC ";
	
	Session session = getSession();
	query=session.createSQLQuery(sql).
	addScalar("name",Hibernate.STRING).addScalar("id",Hibernate.INTEGER).
	addScalar("default_image",Hibernate.STRING)
	.addScalar("offer_price",Hibernate.BIG_DECIMAL)
	.addScalar("actual_price",Hibernate.BIG_DECIMAL)
	.addScalar("category_id",Hibernate.INTEGER)
	.addScalar("total_likes",Hibernate.INTEGER)
.addScalar("is_featured",Hibernate.BOOLEAN)

	;
	prodList=query.list();
	}
	catch(Exception e)
	{
		
		
	}

	return prodList;
	
}

public AppDTO getProductListByCategory(AppDTO appDTO)
{
	Collection productList=Collections.EMPTY_LIST;
	Map dataMap=appDTO.getControllerMap();
	
	String catId=(String) dataMap.get("CATEGORY_ID");
	String priceFilter=(String) dataMap.get("priceFilter");
	
	
	
	String sql="select p.* from product as p where category_id in ("+catId+") and active_status=true and quantity>0 and date_available <= '"+DateUtils.getCurrentDate()+"'";

	if(!CommonUtils.isEmpty(priceFilter))
	{
		String[] splitArr=priceFilter.split("-");
	      for (int i=0; i<splitArr.length;i++) 
	      {
	    	  String filterVal=splitArr[i];
	    	  
	    	  if(!CommonUtils.isEmpty(filterVal))
	    	  {
	    		  sql+=i==0?" and (":" or ";
	    	  
	    	  if(CommonUtils.isInteger(filterVal))
	    	  {
	    		  sql+="  offer_price > "+filterVal;
	    	  }
	    	  else
	    	  {
	    		  String[] values=filterVal.split("_");
	    		  sql+="   offer_price between "+values[0] +" and "+values[1];
	    	  }
	    	  
	    	  }
	    	  
	    	  if(i==(splitArr.length-1)){ sql+=" ) ";  }
	    	  
	      }
	}
	
	sql+=" order by date_modified desc";
	
	Session session =getSession();
	SQLQuery sqlQuery=session.createSQLQuery(sql).addEntity("p", Product.class);
	
	productList=sqlQuery.list();
	dataMap.put("PRODUCT_LIST",productList);
	dataMap.put("RESULT_COUNT", productList.size());
	appDTO.setResponseStatus(ResponseStatus.SUCCESS);
	
	
	
	return appDTO;
}

public boolean isProductExists(int productId)
{
	
	Long count=(Long)getHibernateTemplate()
	.execute(
		new UniqueResultHibCallback(
			"product.isProductExists",
			new String[] { "prodId" },
			new Object[] { productId }));
	
	if(count.intValue()==0)
	{
		return false;
	}
	
	return true;
	
	
	
}


public Product getProductById(int productId){
	return getHibernateTemplate().get(Product.class, productId);
}

public Collection getProductImages(int productId)
{
	Collection imagesColl=getHibernateTemplate().findByNamedQueryAndNamedParam(
			"pi.getProductImagesByProduct",
			new String[] { "productId"},
			new Object[] { productId});
	
	return imagesColl;
	
	
}



public Collection getProductSizes(int productId)
{
	Collection sizesColl=Collections.EMPTY_LIST;
		
		String sql="select {si.*} from size as si join product_to_size as ps on ps.size_id=si.id where si.active_status=true and ps.product_id="+productId+" order by si.sort_order";
	

Session session =getSession();
SQLQuery sqlQuery=session.createSQLQuery(sql).addEntity("si", Size.class);
sizesColl=sqlQuery.list();


	return sizesColl;
}


public Collection getMatchingProducts(int productCatId)
{
	
	SQLQuery query=null;
	Collection resultList=Collections.EMPTY_LIST;
	try{
	String sql="select {proc.*} from (select p.name ,"+
"p.id,p.vendor_id,p.gender,p.quantity,p.default_image,p.actual_price,p.offer_price,p.date_available,p.active_status,p.date_added ,"+
"p.description,p.actual_color,p.total_views,p.total_likes,p.date_modified,p.category_id,p.is_featured, "+
"@cur/*'*/:=/*'*/IF(p.category_id=@cat_id, @cur+1, 1) AS RowNumber, "+
"@cat_id /*'*/:=/*'*/ p.category_id   "+
" from product p "+
 "CROSS JOIN "+
" (SELECT @cat_id /*'*/:=/*'*/(SELECT MIN(p.category_id) FROM product p), @cur/*'*/:=/*'*/0) AS init "+  
" where p.active_status=true and  p.category_id in( "+
" ( "+
"select id from category c5 where c5.parent_id in  (select c.id from category c where c.parent_id=(SELECT t1.id "+
"		 FROM category AS t1 "+
"			  JOIN category AS t2 ON t2.parent_id = t1.id "+
"			  JOIN category AS t3 ON t3.parent_id = t2.id"+
"			 where t1.parent_id is null  and t3.id="+productCatId+"))  and c5.id <>"+productCatId+
")"+
")  "+
"order by RowNumber limit 4"+
") proc ";
	
	
	
	Session session = getSession();
	query=session.createSQLQuery(sql).
	addEntity("proc",Product.class)
	;
	resultList=query.list();
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
return resultList;

	
	
	
}



public Collection getSimilarProducts(int productId)
{
	Product product=getHibernateTemplate().get(Product.class,productId);
	HibernateTemplate ht=getHibernateTemplate();
	ht.setMaxResults(3);
	Collection similarProducts= ht.findByNamedQueryAndNamedParam("prod.fetchSimilarPorducts",
			new String[]{"prodCatId","prodId"},
			new Object[]{ product.getCategory().getId(),product.getId()});
	ht.setMaxResults(0);
	return similarProducts;

}

@Override
public boolean isPincodeExists(int pincode) {

	Collection pincodeColl=getHibernateTemplate().findByNamedQueryAndNamedParam("delAvail.isDeliveryAvailable", new String[]{"pincode"},
			new Object[]{ pincode});
	if(pincodeColl.size()>0)
		return true;
	else
	
	return false;
}



@Override
public Collection getProductDesignAttrSpecificationCount(int productId) {

	getHibernateTemplate().clear();
	
	return getHibernateTemplate().findByNamedQueryAndNamedParam(
			"ptd.productToDesignAttributeSpecCount", 
			new String[]{"productId"},
			new Object[]{ productId});
	
}


public Collection getProductDesigns(int productId)
{
	
	Collection productDesigns=getHibernateTemplate().findByNamedQueryAndNamedParam(
			"prod.getProductDesigns", 
			new String[]{"productId"},
			new Object[]{ productId});
	
	return productDesigns;
}


public Size getSizeById(int sizeId)
{
	return getHibernateTemplate().get(Size.class, sizeId);
}

public DesignAttributeSpecification getDesignAttributeSpecById(int id)
{
	return getHibernateTemplate().get(DesignAttributeSpecification.class, id);
}


public void addOrderReturn(Returns returns)
{

	getHibernateTemplate().save(returns);
}


public ReturnStatus getReturnStatusByName(String name)
{

	return (ReturnStatus)getHibernateTemplate().execute(
			new UniqueResultHibCallback(
					"retStat.getReturnStatusByName", new String[] {
							"name"},
					new Object[] { name}));
	
}

	
}
