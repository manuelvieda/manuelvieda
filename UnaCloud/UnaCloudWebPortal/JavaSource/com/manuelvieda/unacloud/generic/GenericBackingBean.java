/**
 * UnaCloud - High Performance Computing on the Cloud
 * Manuel Eduardo Vieda Salomón (mail@manuelvieda.com)
 *
 * Creation: 15/11/2011
 * Project: UnaCloudWebPortal
 * Package: com.manuelvieda.unacloud.generic
 * File:    GenericBackingBean.java
 */
package com.manuelvieda.unacloud.generic;

import java.util.Map;

import javax.faces.context.FacesContext;

/**
 * Generic Backing Bean Functionalities
 * @author Manuel Eduardo Vieda Salomon  (mail@manuelvieda.com)
 * @version	1.0
 * @since	1.0
 */
public class GenericBackingBean {

	/**
	 * Obtains the {@link FacesContext}
	 * @return
	 */
	public FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}
	
	/**
	 * Obtains the {@link Map} with parameters associated to Request
	 * @return
	 */
	public Map<String, String> getRequestParam() {
		return getFacesContext().getExternalContext().getRequestParameterMap();
	}
	
	/**
	 * Obtains the {@link Map} with atributes associated to request scope
	 * @return
	 */
	public Map<String, Object> getRequestScope() {
		return getFacesContext().getExternalContext().getRequestMap();
	}
	
	/**
	 * Obtains the {@link Map} with atributes associated to session scope
	 * @return
	 */
	public Map<String, Object> getSessionScope() {
		return getFacesContext().getExternalContext().getSessionMap();
	}
	
	/**
	 * Obtains the external context response
	 * @return
	 */
	public Object getExternalRequest() {
		return getFacesContext().getExternalContext().getRequest();
	}
	
	
}
