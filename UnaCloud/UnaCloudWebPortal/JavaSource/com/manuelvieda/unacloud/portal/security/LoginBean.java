/**
 * UnaCloud - High Performance Computing on the Cloud
 * Manuel Eduardo Vieda Salomón (mail@manuelvieda.com)
 *
 * Creation: 19/09/2011
 * Project: UnaCloudWebPortal
 * Package: com.manuelvieda.portal.security
 * File:    LoginBean.java
 */
package com.manuelvieda.unacloud.portal.security;

import java.io.IOException;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;

import com.manuelvieda.unacloud.beans.user.UserBeanLocal;

/**
 * BakingBean for user's portal session managment 
 * @author  Manuel Eduardo Vieda (mail@manuelvieda.com)
 * @version 1.0
 * @since   1.0
 */
@ManagedBean (name="loginBean")
public class LoginBean {
	

	/**
	 * Reference to the EJB that contains and controlls the user information
	 */
	@EJB
	private UserBeanLocal userBean;
	
	
	// -----------------------------------------------------------
	
	/**
	 * Default constructor
	 */
	public LoginBean() {
		super();
	}
	
	
	// -----------------------------------------------------------
	
	/**
	 * Obtains a String with grettings phrase
	 * @return String
	 */
	public String getUserGrettings(){
		return userBean.getUserGrettings();
	}
	
	
	// -----------------------------------------------------------
	


	/**
	 * Logout Action. Removes and invalidate the session of the user.
	 * @throws IOException - if an input/output error occurs
	 */
	public void doLogout() throws IOException {
		userBean.removeSession();
	}
	

}
