package com.manuelvieda.unacloud.beans.user;
import java.io.IOException;

import javax.ejb.Local;

@Local
public interface UserBeanLocal {
	
	/**
	 * Invalidate and Removes the session of the current logged user. 
	 * @throws IOException - if an input/output error occurs
	 */
	public void removeSession() throws IOException;

	/**
	 * Returns a gretting for the current user.
	 * @return String containing a greeting.
	 */
	public String getUserGrettings();
	
	
	/**
	 * Return the username or user id of the current logged user
	 * @return String with the username
	 */
	public String getUsername();
}
