/**
 * UnaCloud - High Performance Computing on the Cloud
 * Manuel Eduardo Vieda Salomón (mail@manuelvieda.com)
 *
 * Creation: 27/10/2011
 * Project: UnaCloudEntities
 * Package: com.manuelvieda.unacloud.repository.impl
 * File:    JpaStateDao.java
 */
package com.manuelvieda.unacloud.repository.impl;

import javax.ejb.Stateless;

import com.manuelvieda.unacloud.entities.general.State;
import com.manuelvieda.unacloud.repository.dao.StateDao;

/**
 * Session Bean implementation class StateDao
 * @author Manuel Eduardo Vieda Salomon  (mail@manuelvieda.com)
 * @version	1.0
 * @since	1.0
 */
@Stateless(mappedName="roleDao")
public class JpaStateDao extends JpaGeneric implements StateDao {

	
	/*
	 * (non-Javadoc)
	 * @see com.manuelvieda.unacloud.repository.dao.StateDao#find(int)
	 */
	@Override
	public State find(int id) {
		return entityManager.find(State.class, id);
	}

}
