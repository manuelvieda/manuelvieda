/**
 * UnaCloud - High Performance Computing on the Cloud
 * Manuel Eduardo Vieda Salomón (mail@manuelvieda.com)
 *
 * Creation: 24/10/2011
 * Project: UnaCloudEJB
 * Package: com.manuelvieda.unacloud.repository.dao
 * File:    JpaUserDao.java
 */
package com.manuelvieda.unacloud.repository.impl;

import javax.ejb.Stateless;
import javax.persistence.EntityTransaction;

import com.manuelvieda.unacloud.entities.general.Role;
import com.manuelvieda.unacloud.entities.general.State;
import com.manuelvieda.unacloud.entities.general.User;
import com.manuelvieda.unacloud.repository.dao.UserDao;

/**
 * Session Bean implementation class UserDao
 * @author 	Manuel Eduardo Vieda Salomon  (mail@manuelvieda.com)
 * @version	1.0
 * @since	1.0
 */
@Stateless(mappedName = "UserDao")
public class JpaUserDao extends JpaGeneric implements UserDao{

	
	/**
	 * Defaul Constructor
	 */
	public JpaUserDao() {
		super();
	}

	// -------------------------------------------------------------------------
	
	/*
	 * (non-Javadoc)
	 * @see com.manuelvieda.unacloud.repository.dao.UserDao#find(java.lang.String)
	 */
	@Override
	public User find(String username) {
		return entityManager.find(User.class, username);
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see com.manuelvieda.unacloud.repository.dao.UserDao#createUser(com.manuelvieda.unacloud.entities.general.User)
	 */
	@Override
	public void createUser(User user){
		
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		
		Role role = entityManager.find(Role.class, 1);
		user.setRole(role);
		
		State state = entityManager.find(State.class, 1);
		user.setStateBean(state);
		entityManager.persist(user);
		entityTransaction.commit();
	}

}
