package com.org.fashtag.dao;

import java.util.Collection;
import com.org.fashtag.AppDTO;
import com.org.fashtag.model.State;

public interface CommonDao extends BaseDao{
	
	public boolean isAllRequiredMeasurementsAvail(int userId);
	
	public State getStateById(int id);
}
