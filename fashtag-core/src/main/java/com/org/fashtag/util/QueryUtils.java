package com.org.fashtag.util;
/**
* @author(name="vipin c p") 
*/
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.hql.classic.GroupByParser;
import org.hibernate.type.Type;

import com.org.fashtag.dao.impl.BaseDaoImpl;

public class QueryUtils extends BaseDaoImpl{

	private static final String FROM_WORD = " from ";

	private static final String SELECT_DISTINCT_WORD = "select distinct ";

	private static ThreadLocal threadBeanClass = new ThreadLocal();

	private static ThreadLocal threadHqlQuery = new ThreadLocal();

	private static ThreadLocal threadAttributeNames = new ThreadLocal();

	/**
	 * This is used for executing an hql query which return an unique result.
	 * 
	 * @param query
	 * @param pareameters
	 *            can be null if no parameter is to be set
	 * @return
	 */
	public Object executeUniqueHQL(String query, Object[] params) {
		initialize(query, false);
		Session session = null;
		try {
			session = getSession();
			String hqlQuery = (String) threadHqlQuery.get();
			// Create query
			Query qry = session.createQuery(hqlQuery);
			if (params != null) {
				for (int i = 0; i < params.length; i++) {
					Object object = params[i];
					if (object != null) {
						qry.setParameter(i, object);
					}
				}
			}
			qry.setMaxResults(1);
			return executeUniqueQuery(qry);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				session.close();
			} catch (Exception e) {

				e.printStackTrace();
			}
		}
		return null;
	}
	
	

	/**
	 *
	 * Intialize for HQL
	 */
	private void initialize(String query, boolean readAlias) {
		initialize(query, readAlias, false);
	}
	
	
	

	private void initialize(String query, boolean readAlias, boolean preSecurityNeeded) {
		Session session = null;
		try {
			session = getSession();
			Query qry = session.createQuery(query);
			if (preSecurityNeeded) {
				init(qry, readAlias, true);
			} else {
				init(qry, readAlias);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
	
	
	

	
	/**
	 * 
	 * Intialize for Query Object
	 */
	private void init(Query qry, boolean readAlias, boolean preSecurityNeeded) {
		Session session = null;
		try {
			session = getSession();
			String hqlQuery = qry.getQueryString();
			hqlQuery = qry.getQueryString();

			// Prepare a Hibernate query from "from"
			String hqlFromClause = extractFromClause(hqlQuery);
			Query query = session.createQuery(hqlFromClause);
			session.close();

			// Determine the return type for this query
			Type beanType = query.getReturnTypes()[0];

			// Return the class instance from the query.
			Class beanClass = beanType.getReturnedClass();


			String[] columns = extractColumns(hqlQuery);
			// Atleas single field is required to fetch in select query
			if (columns == null) {
				throw new RuntimeException(
"Invalid Query, Possible cause: Atleast One column is required in the select in the query");
			}
			String attributeNames[] = null;
			// //Read Alias names for eg: select a.id as myId from ....
			if (readAlias) {
				attributeNames = getAttributeFieldNames(columns, readAlias);
			} else {
				attributeNames = getAttributeFieldNames(columns, readAlias);
			}
			threadHqlQuery.set(hqlQuery);
			threadBeanClass.set(beanClass);
			threadAttributeNames.set(attributeNames);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session.isOpen())
				session.close();
		}
	}
	
	
	
	/** Intialize for Query Object */
	private void init(Query qry, boolean readAlias) {
		Session session = null;
		try {
			session = getSession();
			String hqlQuery = qry.getQueryString();
			hqlQuery = qry.getQueryString();

			// Prepare a Hibernate query from "from"
			String hqlFromClause = extractFromClause(hqlQuery);
			Query query = session.createQuery(hqlFromClause);
			session.close();

			// Determine the return type for this query
			Type beanType = query.getReturnTypes()[0];

			// Return the class instance from the query.
			Class beanClass = beanType.getReturnedClass();

			// list of columns with alias name
			String[] columns = extractColumns(hqlQuery);

			// Atleas single field is required to fetch in select query
			if (columns == null) {
				throw new RuntimeException(
						"Invalid Query, Possible cause: Atleast One column is required in the select in the query");
			}
			String attributeNames[] = null;
			// //Read Alias names for eg: select a.id as myId from ....
			if (readAlias) {// here myId will be used as attrinute
				attributeNames = getAttributeFieldNames(columns, readAlias);
			} else {// here id will be used as attrinute
				attributeNames = getAttributeFieldNames(columns, readAlias);
			}

			threadHqlQuery.set(hqlQuery);
			threadBeanClass.set(beanClass);
			threadAttributeNames.set(attributeNames);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session.isOpen())
				session.close();
		}
	}
	
	
	
	/**
	 * Private method execute query and set the values for the object.
	 * 
	 * @param query
	 * @return
	 * @throws Exception
	 * @return Object
	 * 
	 */
	private Object executeUniqueQuery(Query query) throws Exception {

		Object bean = null;
		String[] attributeNames = (String[]) threadAttributeNames.get();
		// if query has only single return value
		if (attributeNames.length > 1) {
			Object[] row = (Object[]) query.uniqueResult();
			if (row != null) {
				Class beanClass = (Class) threadBeanClass.get();
				bean = beanClass.newInstance();
				for (int j = 0; j < row.length; j++) {
					if (row[j] != null) {
						AppUtils.initialisePath(bean, attributeNames[j]);
						PropertyUtils.setProperty(bean, attributeNames[j],
								row[j]);
					}
				}
			}
		} else {
			Object row = (Object) query.uniqueResult();
			if (row != null) {
				Class beanClass = (Class) threadBeanClass.get();
				bean = beanClass.newInstance();
				AppUtils.initialisePath(bean, attributeNames[0]);
				PropertyUtils.setProperty(bean, attributeNames[0], row);
			}
		}
		return bean;
	}
	
	
	private static String extractFromClause(final String query) {
		int fromPosition = query.toLowerCase().indexOf(" from ");
		if (fromPosition >= 0) {
			return query.substring(fromPosition);
		} else {
			return query;
		}
	}
	
	private static String[] getAttributeFieldNames(final String[] fieldNames,
			boolean readAlias) {
		ArrayList list = new ArrayList();
		for (int i = 0; i < fieldNames.length; i++) {
			list.add(getAttributeFieldName(fieldNames[i], readAlias));
		}
		return (String[]) list.toArray(new String[0]);
	}
	
	
	private static String getAttributeFieldName(final String fieldName,
			boolean readAlias) {
		int dot = fieldName.indexOf('.');
		String trimmedFieldName = null;
		String normalisedFieldName = null;
		if (dot >= 0) {
			trimmedFieldName = fieldName.substring(dot + 1);
		} else {
			trimmedFieldName = fieldName;
		}

		int asClause = trimmedFieldName.toLowerCase().indexOf(" as ");
		if (readAlias) {
			if (asClause > 0) {
				normalisedFieldName = trimmedFieldName.substring(asClause
						+ " as ".length(), trimmedFieldName.length());
				normalisedFieldName = normalisedFieldName.replaceAll("\'", "");
				normalisedFieldName = normalisedFieldName.replaceAll("\"", "");
			} else {
				normalisedFieldName = trimmedFieldName;
			}
		} else {
			if (asClause > 0) {
				normalisedFieldName = trimmedFieldName.substring(0, asClause);
			} else {
				normalisedFieldName = trimmedFieldName;
			}
		}
		return normalisedFieldName;
	}
	
	
	
	
	
	
	private static String[] extractColumns(final String query) {
		try {
			int fromPosition = query.toLowerCase().indexOf(" from ");
			int selectPosition = query.toLowerCase()
					.indexOf("select distinct ");
			if (selectPosition >= 0) {
				if (fromPosition < selectPosition) {
					selectPosition = query.toLowerCase().indexOf("select ") + 6;
				} else {
					selectPosition = selectPosition + 15;
				}
			} else {
				selectPosition = query.toLowerCase().indexOf("select ") + 6;
			}
			// int selectPosition = query.toLowerCase().indexOf("select ");
			if (selectPosition >= 0) {
				String columns = query.substring(selectPosition, fromPosition);
				StringTokenizer st = new StringTokenizer(columns, ",");
				List columnList = new ArrayList();
				while (st.hasMoreTokens()) {
					columnList.add(st.nextToken().trim());
				}
				return (String[]) columnList.toArray(new String[0]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	/**
	 * 
	 * Execute an hql query which returns a list.
	 * 
	 * @param query
	 * @param pareameters
	 *            can be null if no parameter is to be set
	 * @return
	 */
	public List executeHQL(String query, Object[] pareameters) {
		return executeHQL(query, pareameters, false);
	}
	
	/**
	 * 
	 * Execute an hql query which returns a list.
	 * 
	 * @param query
	 * @param pareameters
	 *            can be null if no parameter is to be set
	 * @return
	 */
	public List executeHQL(String query, Object[] pareameters,
			boolean preSecurityNeeded) {
		return executeHQL(query, pareameters, preSecurityNeeded, null, null);
	}
	
	
	/* 
	 * Execute an hql query which returns a list. This query fetch the result
	 * with offset and limit
	 * 
	 * @param query
	 * @param pareameters
	 *            can be null if no parameter is to be set
	 * @return
	 */
	public List executeHQL(String query, Object[] pareameters,
			boolean preSecurityNeeded, Integer offset, Integer limit) {
		if (preSecurityNeeded) {
			initialize(query, false, true);
		} else {
			initialize(query, false);
		}
		Session session = null;
		try {
			session = getSession();
			String hqlQuery = (String) threadHqlQuery.get();
			// Create query
			Query qry = session.createQuery(hqlQuery);
			if (pareameters != null) {
				for (int i = 0, j = 0; i < pareameters.length; i++) {
					Object object = pareameters[i];
					if (object != null) {
						qry.setParameter(j, object);
						j++;
					}
				}
			}
			if (offset != null && limit != null) {
				qry.setFirstResult(offset);
				qry.setMaxResults(limit);
			}
			return executeHibQuery(qry);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				session.close();
			} catch (Exception e) {

				e.printStackTrace();
			}
		}
		return Collections.EMPTY_LIST;
	}
		
	
	private List executeHibQuery(Query query) throws Exception {

		List results = new ArrayList();

		// Execute query
		Iterator iter = query.iterate();
		while (iter.hasNext()) {
			Class beanClass = (Class) threadBeanClass.get();
			Object bean = beanClass.newInstance();
			String[] attributeNames = (String[]) threadAttributeNames.get();
			// if query has only single return value
			if (attributeNames.length > 1) {
				Object[] row = (Object[]) iter.next();
				for (int j = 0; j < row.length; j++) {
					if (row[j] != null) {
						AppUtils.initialisePath(bean, attributeNames[j]);
						PropertyUtils.setProperty(bean, attributeNames[j],
								row[j]);
					}
				}
			} else {
				Object row = (Object) iter.next();
				if (row != null) {
					AppUtils.initialisePath(bean, attributeNames[0]);
					PropertyUtils.setProperty(bean, attributeNames[0], row);
				}
			}
			results.add(bean);
		}
		return results;
	}
	
	
	
	public int executeUpdateQuery(String query) {

		Session session = getSession();
		try {
			Query hqlQuery = session.createQuery(query);
			return hqlQuery.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.flush();
			session.close();
		}
		return -1;
	}
	
	/**
	 * Find and Execute named query with parameter and value.
	 * 
	 * @param queryName
	 * @param param
	 * @param value
	 * @return List
	 * 
	 */
	public List findByNamedQuery(String queryName, String param, Object value) {
		// List results = new ArrayList();
		init(queryName);
		Session session = null;
		try {
			session = getSession();
			String hqlQuery = (String) threadHqlQuery.get();
			// Create & Set Param query
			Query query = session.createQuery(hqlQuery);
			if (param != null && !param.equals("")) {
				query.setParameter(param, value);
			}
			return executeHibQuery(query);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				session.close();
			} catch (Exception e) {

				e.printStackTrace();
			}
		}
		return Collections.EMPTY_LIST;
	}
	
	
	/**
	 * Find and Execute named query with multiplr parameters and values.
	 * 
	 * @param queryName
	 * @param params
	 * @param values
	 * 
	 * @return List
	 * 
	 */
	public List findByNamedQuery(String queryName, String[] params,
			Object[] values) {
		init(queryName);
		Session session = null;
		try {
			session = getSession();
			String hqlQuery = (String) threadHqlQuery.get();
			// Create & Set Param query
			Query query = session.createQuery(hqlQuery);
			for (int i = 0; i < params.length; i++)
				query.setParameter(params[i], values[i]);

			return executeHibQuery(query);
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (session.isOpen()) {
				session.close();
			}
		}
		return Collections.EMPTY_LIST;
	}


	/** Intialize for Named query */
	private void init(String namedQuery) {
		Session session = null;
		Query qry = null;
		try {
			session = getSession();
			qry = session.getNamedQuery(namedQuery);
			init(qry, false);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session.isOpen())
				session.close();
		}
	}
	
	
}
