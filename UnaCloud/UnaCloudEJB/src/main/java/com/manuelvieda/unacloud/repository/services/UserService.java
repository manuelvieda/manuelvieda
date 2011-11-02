/**
 * UnaCloud - High Performance Computing on the Cloud
 * Manuel Eduardo Vieda Salomón (mail@manuelvieda.com)
 *
 * Creation: 27/10/2011
 * Project: UnaCloudEJB
 * Package: com.manuelvieda.unacloud.repository.services
 * File:    UserService.java
 */
package com.manuelvieda.unacloud.repository.services;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.manuelvieda.unacloud.entities.general.User;
import com.manuelvieda.unacloud.repository.dao.UserDao;

/**
 * Session Bean implementation class UserService
 * @author Manuel Eduardo Vieda Salomon  (mail@manuelvieda.com)
 * @version	1.0
 * @since	1.0
 */
@LocalBean
@Stateless
public class UserService {
	
	@EJB
	private UserDao userDao;
	
	/**
	 * Finds a user
	 * @param username
	 * @return
	 */
	public User findUser(String username){
		return userDao.find(username);
	}

}
