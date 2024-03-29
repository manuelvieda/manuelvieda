/**
 * UnaCloud - High Performance Computing on the Cloud
 * Manuel Eduardo Vieda Salom�n (mail@manuelvieda.com)
 *
 * Creation: 27/10/2011
 * Project: UnaCloudEntities
 * Package: com.manuelvieda.unacloud.repository.impl
 * File:    JpaApplicationDao.java
 */
package com.manuelvieda.unacloud.repository.impl;

import java.util.List;

import javax.ejb.Stateless;

import com.manuelvieda.unacloud.entities.general.Application;
import com.manuelvieda.unacloud.repository.constants.Constants;
import com.manuelvieda.unacloud.repository.dao.ApplicationDao;

/**
 * Session Bean implementation class ApplicationDao
 * @author Manuel Eduardo Vieda Salomon  (mail@manuelvieda.com)
 * @version	1.0
 * @since	1.0
 */
@Stateless(mappedName="applicationDao")
public class JpaApplicationDao extends JpaGeneric implements ApplicationDao {

	/* (non-Javadoc)
	 * @see com.manuelvieda.unacloud.repository.dao.ApplicationDao#find(int)
	 */
	@Override
	public Application find(int id) {
		return entityManager.find(Application.class, id);
	}

	/* (non-Javadoc)
	 * @see com.manuelvieda.unacloud.repository.dao.ApplicationDao#getAllApplications()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Application> getAllApplications() {
		return entityManager.createNamedQuery(Constants.NQ_APPLICATION_FINAL_ALL)
				.getResultList();
	}

}
