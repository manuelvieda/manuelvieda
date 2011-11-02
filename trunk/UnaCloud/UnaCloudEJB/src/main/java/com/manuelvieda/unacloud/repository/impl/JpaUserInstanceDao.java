/**
 * UnaCloud - High Performance Computing on the Cloud
 * Manuel Eduardo Vieda Salomón (mail@manuelvieda.com)
 *
 * Creation: 27/10/2011
 * Project: UnaCloudEntities
 * Package: com.manuelvieda.unacloud.repository.impl
 * File:    JpaUserInstanceDao.java
 */
package com.manuelvieda.unacloud.repository.impl;

import java.util.List;

import javax.ejb.Stateless;

import com.manuelvieda.unacloud.entities.general.UserInstance;
import com.manuelvieda.unacloud.repository.constants.Constants;
import com.manuelvieda.unacloud.repository.dao.UserInstanceDao;

/**
 * Session Bean implementation class UserInstanceDao
 * @author Manuel Eduardo Vieda Salomon  (mail@manuelvieda.com)
 * @version	1.0
 * @since	1.0
 */
@Stateless(mappedName="userUserInstanceDao")
public class JpaUserInstanceDao extends JpaGeneric implements UserInstanceDao {

	/*
	 * (non-Javadoc)
	 * @see com.manuelvieda.unacloud.repository.dao.UserInstanceDao#createUserInstance(com.manuelvieda.unacloud.entities.general.UserInstance)
	 */
	@Override
	public void createUserInstance(UserInstance userInstance) {
		entityManager.persist(userInstance);
		
	}

	/*
	 * (non-Javadoc)
	 * @see com.manuelvieda.unacloud.repository.dao.UserInstanceDao#getUserInstances(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<UserInstance> getUserInstances(String username) {
		return entityManager.createNamedQuery(Constants.NQ_USER_INSTANCES_FIND_BY_USER)
				.setParameter("username", username)
				.getResultList();
	}

}
