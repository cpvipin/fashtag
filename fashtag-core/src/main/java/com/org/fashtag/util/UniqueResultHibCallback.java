
package com.org.fashtag.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 * @author Vipin C P
 */
public class UniqueResultHibCallback implements HibernateCallback {

    private Map paramMap;

    private String queryName;

    public UniqueResultHibCallback(String queryName, String paramName, Object paramValue) {
        this(queryName, Collections.singletonMap(paramName, paramValue));
    }

    public UniqueResultHibCallback(String aQueryName, String paramName1, Object paramValue1, String paramName2, Object paramValue2) {
        queryName = aQueryName;
        paramMap = new HashMap();
        paramMap.put(paramName1, paramValue1);
        paramMap.put(paramName2, paramValue2);
    }
    
    public UniqueResultHibCallback(String aQueryName, String[] paramName, Object[] paramValue) throws HibernateException {
        queryName = aQueryName;
        paramMap = new HashMap();
        if(paramName!=null&&paramValue!=null&&paramName.length==paramValue.length){
        for(int i=0;i<paramName.length;i++){
        paramMap.put(paramName[i], paramValue[i]);}
        } else{
        throw  new HibernateException("Size of paramName[]is not equal to that of paramValue[]");
        }
    }
    
    public UniqueResultHibCallback(String aQueryName, Map aParamMap) {
        queryName = aQueryName;
        paramMap = aParamMap;
    }
    
    public UniqueResultHibCallback(String aQueryName) {
        queryName = aQueryName;
        paramMap = Collections.EMPTY_MAP;
    }

    public Object doInHibernate(Session session) throws HibernateException {
        Object result = null;
		try {
			Query tSavedQuery = session.getNamedQuery(queryName);
			for (Iterator paramIter = paramMap.entrySet().iterator(); paramIter.hasNext();) {
			    Entry entry = (Map.Entry) paramIter.next();
			    tSavedQuery.setParameter((String) entry.getKey(), entry.getValue());
			}
			result = tSavedQuery.uniqueResult();
		} catch (HibernateException e) {			
			e.printStackTrace();
		}finally{
			session.flush();
			session.close();			
		}
        
        return result;
    }
}