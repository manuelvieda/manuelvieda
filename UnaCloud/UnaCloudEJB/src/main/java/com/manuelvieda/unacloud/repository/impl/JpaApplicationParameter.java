/**
 * UnaCloud - High Performance Computing on the Cloud
 * Manuel Eduardo Vieda Salomón (mail@manuelvieda.com)
 *
 * Creation: 27/10/2011
 * Project: UnaCloudEntities
 * Package: com.manuelvieda.unacloud.repository.impl
 * File:    JpaApplicationParameter.java
 */
package com.manuelvieda.unacloud.repository.impl;

import java.util.List;

import javax.ejb.Stateless;

import com.manuelvieda.unacloud.entities.general.ApplicationParameter;
import com.manuelvieda.unacloud.repository.constants.Constants;
import com.manuelvieda.unacloud.repository.dao.ApplicationParameterDao;

/**
 * Session Bean implementation class ApplicationParametere
 * @author Manuel Eduardo Vieda Salomon  (mail@manuelvieda.com)
 * @version	1.0
 * @since	1.0
 */
@Stateless(mappedName="applicationParameter")
public class JpaApplicationParameter extends JpaGeneric implements ApplicationParameterDao {
	
	/*
	 * (non-Javadoc)
	 * @see com.manuelvieda.unacloud.repository.dao.ApplicationParameterDao#getApplicationParameters(int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ApplicationParameter> getApplicationParameters(int idAplication){
		return entityManager.createNamedQuery(Constants.NQ_APPLICATION_PARAMETERS_GET_BY_APP)
				.setParameter("idAplication", idAplication)
				.getResultList();
	}

	/* (non-Javadoc)
	 * @see com.manuelvieda.unacloud.repository.dao.ApplicationParameterDao#find(int)
	 */
	@Override
	public ApplicationParameter find(int id) {
		return entityManager.find(ApplicationParameter.class, id);
	}

}
