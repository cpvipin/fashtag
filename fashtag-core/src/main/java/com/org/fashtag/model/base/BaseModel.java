package com.org.fashtag.model.base;

import java.io.Serializable;
/**
* @author(name="vipin c p") 
*/
public abstract class BaseModel implements Serializable {
	 
	
	public BaseModel() {
	        super();
	    }
	

	//Transient Field used for Global Indexing
	private String globalIndex;

	public String getGlobalIndex() {
		return globalIndex;
	}

	public void setGlobalIndex(String globalIndex) {
		this.globalIndex = globalIndex;
	}
	
	abstract public Integer getId();
	
	public String toString() {
        return this.getClass().getName() + " id = " + getId();
    }

    public boolean equals(Object other) {
        Integer thisId = getId();
        if (thisId == null) {
            return this == other;
        }
        if (other == null || !getClass().getName().equals(other.getClass().getName())) {
            return false;
        }
        Integer thatId = ((BaseModel) other).getId();
        return (thatId != null) && thisId.equals(thatId);
    }

    public int hashCode() {
        Integer id = getId();
        if (id != null) {
            return id.hashCode();
        }
        return super.hashCode();
    }   

}
