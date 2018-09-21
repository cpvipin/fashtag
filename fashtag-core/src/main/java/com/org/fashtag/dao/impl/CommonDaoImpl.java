package com.org.fashtag.dao.impl;

import java.util.Collection;

import org.hibernate.Session;

import com.org.fashtag.dao.CommonDao;
import com.org.fashtag.hibernate.PaginationCriteria;
import com.org.fashtag.model.State;
import com.org.fashtag.model.base.BaseModel;

public class CommonDaoImpl extends BaseDaoImpl implements CommonDao {

	
public State getStateById(int id)
{
return	getHibernateTemplate().get(State.class, id);
}
	
public boolean isAllRequiredMeasurementsAvail(int userId)
{
	Session session = getSession();

	return false;
}
	
}
