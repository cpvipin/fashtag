package com.org.fashtag.dao;
import java.util.Collection;

import org.hibernate.Session;

import com.org.fashtag.hibernate.PaginationCriteria;
import com.org.fashtag.model.base.BaseModel;
/**
* @author(name="vipin c p") 
*/

public interface BaseDao {
	public void add(BaseModel baseModel);
	public void update(BaseModel baseModel);
	public void delete(BaseModel baseModel);
	

	public Object findObjectByCondition(Class aClass, String[] propertyName,
			Object[] propValue);
	
	public Object findOptimizedObjectByCondition(Class aClass,
			String[] returnProperty, String[] propertyName,
			Object[] propertyValue);
	
	public PaginationCriteria getPaginationCriteria(Session aSession,Class aClass) ;
	
	
	/**
	 * @param aClass
	 * @param returnProperty
	 * @param propertyName
	 * @param propertyValue
	 * @param orderProperty
	 * @param isDescending
	 * @return Collection
	 */
	public Collection findOptimizedListByCondition(Class aClass,
			String[] returnProperty, String[] propertyName,
			Object[] propertyValue, String orderProperty, boolean isDescending, boolean isDistinct);
	
	public Collection findListByCondition(Class aClass, String[] propertyName,
			Object[] propValue);

	public Collection findListByCondition(Class aClass, String[] propertyName,
			Object[] propertyValue, String orderProperty, boolean isDescending);
	
	public Collection findListByCondition(Class aClass, String returnProperty,
			String[] propertyName, Object[] propertyValue,
			String orderProperty, boolean isDescending);
	
	
	
	public Collection findListByLikeSelection(Class aClass,
			String propertyName, String propValue);
	
	public Collection findListByLikeSelection(Class aClass,
			String returnProperty, String propertyName, String propertyValue);
	
	public Collection findListByLikeSelection(Class aClass,
			String returnProperty, String propertyName, String propertyValue,
			String orderProperty, boolean isDescending);
	
	
	public Collection findAll(Class aClass); 
	
	

	public boolean isDuplicateField(Class aClass, String fieldName,
			Object fieldValue, String propertyName, Object propValue);
	
	public int performBulkDelete(Class aClass, String[] propertyName,
			Object[] propertyValue);
	
	
}
