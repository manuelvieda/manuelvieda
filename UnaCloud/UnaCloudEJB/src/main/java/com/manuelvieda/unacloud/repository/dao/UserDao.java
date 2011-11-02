/**
 * UnaCloud - High Performance Computing on the Cloud
 * Manuel Eduardo Vieda Salomón (mail@manuelvieda.com)
 *
 * Creation: 24/10/2011
 * Project: UnaCloudEJB
 * Package: com.manuelvieda.unacloud.repository.dao
 * File:    UserDao.java
 */
package com.manuelvieda.unacloud.repository.dao;

import javax.ejb.Local;

import com.manuelvieda.unacloud.entities.general.User;

/**
 * Interface for Entity Bean implementation class JpaUserDao
 * @author Manuel Eduardo Vieda Salomon  (mail@manuelvieda.com)
 * @version	1.0
 * @since	1.0
 */
@Local
public interface UserDao {
	
	
	/**
	 * Finds the user
	 * @param username - Id/username of the user
	 * @return
	 */
	public User find(String username);
	
	
	/**
	 * Creates a ew user
	 * @param user
	 */
	public void createUser(User user);

}
