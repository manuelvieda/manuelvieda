/**
 * UnaCloud - High Performance Computing on the Cloud
 * Manuel Eduardo Vieda Salomón (mail@manuelvieda.com)
 *
 * Creation: 27/10/2011
 * Project: UnaCloudEntities
 * Package: com.manuelvieda.unacloud.repository.impl
 * File:    JpaInstanceType.java
 */
package com.manuelvieda.unacloud.repository.impl;

import java.util.List;

import javax.ejb.Stateless;

import com.manuelvieda.unacloud.entities.general.InstanceType;
import com.manuelvieda.unacloud.repository.constants.Constants;
import com.manuelvieda.unacloud.repository.dao.InstanceTypeDao;

/**
 * Session Bean implementation class InstanceTypeDao
 * @author Manuel Eduardo Vieda Salomon  (mail@manuelvieda.com)
 * @version	1.0
 * @since	1.0
 */
@Stateless(mappedName="instanceTypeDao")
public class JpaInstanceType extends JpaGeneric implements InstanceTypeDao {

	
	/*
	 * (non-Javadoc)
	 * @see com.manuelvieda.unacloud.repository.dao.InstanceTypeDao#find(int)
	 */
	@Override
	public InstanceType find(int id) {
		return entityManager.find(InstanceType.class, id);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.manuelvieda.unacloud.repository.dao.InstanceTypeDao#getAllInstanceTypeFromCloudProvider(int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<InstanceType> getAllInstanceTypeFromCloudProvider(int idProvider) {
		return entityManager.createNamedQuery(Constants.NQ_INSTANCE_TYPE_GETALLL_BY_PROVIDER)
				.setParameter("idProvider", idProvider)
				.getResultList();
	}
}
