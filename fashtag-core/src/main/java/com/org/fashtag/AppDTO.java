package com.org.fashtag;

import java.io.Serializable;
import java.util.Map;

import com.org.fashtag.common.ResponseStatus;

public class AppDTO implements Serializable {
	

	private Map controllerMap;
	
	private String errorMessage;

	private String infoMessage;
	
	private ResponseStatus responseStatus;
	
	
	
	/* getters and setters*/
	
	

	public ResponseStatus getResponseStatus() {
		return responseStatus;
	}

	public void setResponseStatus(ResponseStatus responseStatus) {
		this.responseStatus = responseStatus;
	}

	public Map getControllerMap() {
		return controllerMap;
	}

	public void setControllerMap(Map controllerMap) {
		this.controllerMap = controllerMap;
	}


	
	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getInfoMessage() {
		return infoMessage;
	}

	public void setInfoMessage(String infoMessage) {
		this.infoMessage = infoMessage;
	}



	
}
