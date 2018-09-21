package com.org.fashtag.dao.impl;

import java.util.Collection;
import java.util.Collections;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.org.fashtag.context.ApplicationContext;
import com.org.fashtag.context.BeanConstants;
import com.org.fashtag.dao.BaseDao;
import com.org.fashtag.dao.impl.QueryGenerator;
import com.org.fashtag.hibernate.PaginationCriteria;
import com.org.fashtag.model.base.BaseModel;
import com.org.fashtag.util.CommonUtils;
import com.org.fashtag.util.QueryUtils;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
/**
* @author(name="vipin c p") 
*/


public class BaseDaoImpl  extends HibernateDaoSupport implements BaseDao  {


	private static ThreadLocal threadSecurityIntercepted = new ThreadLocal();
	
	
	
	public void add(BaseModel baseModel) {
		getHibernateTemplate().save(baseModel);		
	}


	public void update(BaseModel baseModel) {
 	getHibernateTemplate().update(baseModel);
		
	}


	public void delete(BaseModel baseModel) {
		getHibernateTemplate().delete(baseModel);
		
	}
	
	public Object findObjectByCondition(Class aClass, String[] propertyName,
			Object[] propertyValue) {
		return findObjectByCondition(aClass, null, propertyName, propertyValue);
	}
	
	public Object findObjectByCondition(Class aClass, 
			String returnProperty,
			String[] propertyName, Object[] propertyValue) {
		
		
	return executeQueryUnique(QueryGenerator.generateConditionQuery(
				aClass, returnProperty, propertyName, propertyValue, null,
				false), propertyValue);
	}
	
	
	public Object findOptimizedObjectByCondition(Class aClass,
			String[] returnProperty, String[] propertyName,
			Object[] propertyValue) {
		return ((QueryUtils) ApplicationContext.getApplicationContext()
				.getBean(BeanConstants.QUERYUTILS_DAO)).executeUniqueHQL(
				QueryGenerator.generateQueryUtilQuery(aClass,
						returnProperty, propertyName, propertyValue, null,
						false, false), propertyValue);
	}
	
	public Collection findListByLikeSelection(Class aClass,
			String propertyName, String propertyValue) {
		return executeQuery(QueryGenerator.listByLikeSelectionQury(
				aClass, null, propertyName, propertyValue, null, false),
				new Object[] { propertyValue != null ? propertyValue
						.toUpperCase() : propertyValue });
	}
	
	public Collection findListByLikeSelection(Class aClass,
			String returnProperty, String propertyName, String propertyValue) {
		return executeQuery(QueryGenerator.listByLikeSelectionQury(
				aClass, returnProperty, propertyName, propertyValue, null,
				false), new Object[] { propertyValue != null ? propertyValue
				.toUpperCase() : propertyValue });
	}

	public Collection findListByLikeSelection(Class aClass,
			String returnProperty, String propertyName, String propertyValue,
			String orderProperty, boolean isDescending) {
		return executeQuery(QueryGenerator.listByLikeSelectionQury(
				aClass, returnProperty, propertyName, propertyValue,
				orderProperty, isDescending),
				new Object[] { propertyValue != null ? propertyValue
						.toUpperCase() : propertyValue });
	}

	
	
	
	private Collection executeQuery(String queryString, Object[] pareameters) {
		Session session = getSession();
		try {
			Query query = session.createQuery(queryString);
			if (pareameters != null) {
				int j = 0;
				for (int i = 0; i < pareameters.length; i++) {
					Object object = pareameters[i];
					if (object != null) {
						query.setParameter(j, object);
						j++;
					}
				}
			}
			return query.list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.flush();
			session.close();
		}
		return Collections.EMPTY_LIST;
	}
	
	
	private Object executeQueryUnique(String queryString, Object[] pareameters) {
		Session session = getSession();
		try {
			Query query = session.createQuery(queryString);
			if (pareameters != null) {
				int j = 0;
				for (int i = 0; i < pareameters.length; i++) {
					Object object = pareameters[i];
					if (object != null) {
						query.setParameter(j, object);
						j++;
					}
				}
			}
			return query.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.flush();
			session.close();
		}
		return null;
	}
	
	

	public boolean isSecurityIntercepted() 
	{
		Boolean securityIntercepted = (Boolean) threadSecurityIntercepted.get();
		if (securityIntercepted != null) 
		{
		return securityIntercepted.booleanValue();
		}
		return false;
	}

	public void setSecurityIntercepted(boolean securityIntercepted) {
		threadSecurityIntercepted.set(new Boolean(securityIntercepted));
	}
	
	
	
	
	public PaginationCriteria getPaginationCriteria(Session aSession,
			Class aClass) {

		Criteria c1 = aSession.createCriteria(aClass);
		Criteria c2 = aSession.createCriteria(aClass);
		PaginationCriteria pc = PaginationCriteria.getInstance(c1, c2);
		return pc;
	}
	
	
	public Collection findOptimizedListByCondition(Class aClass,
			String[] returnProperty, String[] propertyName,
			Object[] propertyValue, String orderProperty, boolean isDescending,boolean isDistinct) {
		return ((QueryUtils) ApplicationContext.getApplicationContext()
				.getBean(BeanConstants.QUERYUTILS_DAO)).executeHQL(
				QueryGenerator.generateQueryUtilQuery(aClass,
						returnProperty, propertyName, propertyValue,
						orderProperty, isDescending, false, isDistinct), propertyValue);
	}
	
	public Collection findListByCondition(Class aClass, String[] propertyName,
			Object[] propertyValue) {
		return findListByCondition(aClass, null, propertyName, propertyValue,
				null, false);
	}

	public Collection findListByCondition(Class aClass, String[] propertyName,
			Object[] propertyValue, String orderProperty, boolean isDescending) {
		return findListByCondition(aClass, null, propertyName, propertyValue,
				orderProperty, isDescending);
	}

	

	public Collection findListByCondition(Class aClass, String returnProperty,
			String[] propertyName, Object[] propertyValue,
			String orderProperty, boolean isDescending) {
		return executeQuery(QueryGenerator.generateConditionQury(aClass,
				returnProperty, propertyName, propertyValue, orderProperty,
				isDescending), propertyValue);
	}
	
	
	public Collection findAll(Class aClass) {
		return getHibernateTemplate().loadAll(aClass);
	}
	
	
	public boolean isDuplicateField(Class aClass, String fieldName,
			Object fieldValue, String propertyName, Object propValue) {
		Collection resultColl = executeQuery(QueryGenerator
				.generateDuplicationCheckQuery(aClass, fieldName, fieldValue,
						propertyName, propValue), new Object[] { fieldValue,
				propValue });
		if (CommonUtils.isEmpty(resultColl)) {
			return false;
		}
		return true;
	}
	
	
	
	public int performBulkDelete(Class aClass, String[] propertyName,
			Object[] propertyValue) {
		if (propertyName == null || propertyValue == null
				|| propertyName.length != propertyValue.length) {
			throw new RuntimeException(
					"Null or Un-mached property vame-value pair");
		}

		Session session = getSession();
		try {
			Query query = session.createQuery(QueryGenerator
					.generateBulkDeleteQuery(aClass, propertyName,
							propertyValue));
			if (propertyValue != null) {
				int j = 0;
				for (int i = 0; i < propertyValue.length; i++) {
					Object object = propertyValue[i];
					if (object != null) {
						query.setParameter(j, object);
						j++;

					}
				}
			}
			return query.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.flush();
			session.close();
		}
		return -1;
	}
	
	
}
