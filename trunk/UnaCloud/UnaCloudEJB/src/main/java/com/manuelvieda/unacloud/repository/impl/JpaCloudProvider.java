/**
 * UnaCloud - High Performance Computing on the Cloud
 * Manuel Eduardo Vieda Salomón (mail@manuelvieda.com)
 *
 * Creation: 27/10/2011
 * Project: UnaCloudEntities
 * Package: com.manuelvieda.unacloud.repository.impl
 * File:    JpaCloudProvider.java
 */
package com.manuelvieda.unacloud.repository.impl;

import java.util.List;

import javax.ejb.Stateless;

import com.manuelvieda.unacloud.entities.general.CloudProvider;
import com.manuelvieda.unacloud.repository.constants.Constants;
import com.manuelvieda.unacloud.repository.dao.CloudProviderDao;

/**
 * Session Bean implementation class CloudProviderDao
 * @author Manuel Eduardo Vieda Salomon  (mail@manuelvieda.com)
 * @version	1.0
 * @since	1.0
 */
@Stateless(mappedName="cloudProviderDao")
public class JpaCloudProvider extends JpaGeneric implements CloudProviderDao {

	/*
	 * (non-Javadoc)
	 * @see com.manuelvieda.unacloud.repository.dao.CloudProviderDao#findCloudProvider(int)
	 */
	@Override
	public CloudProvider findCloudProvider(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.manuelvieda.unacloud.repository.dao.CloudProviderDao#getCloudProvidersList()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CloudProvider> getCloudProvidersList() {
		return entityManager.createNamedQuery(Constants.NQ_CLOUD_PROVIDER_FIND_ALL).getResultList();
	}


}
