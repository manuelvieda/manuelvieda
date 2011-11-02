/**
 * UnaCloud - High Performance Computing on the Cloud
 * Manuel Eduardo Vieda Salomón (mail@manuelvieda.com)
 *
 * Creation: 27/10/2011
 * Project: UnaCloudEntities
 * Package: com.manuelvieda.unacloud.repository.dao
 * File:    CloudProviderDao.java
 */
package com.manuelvieda.unacloud.repository.dao;

import java.util.List;

import javax.ejb.Local;

import com.manuelvieda.unacloud.entities.general.CloudProvider;

/**
 * Interface for Entity Bean implementation class JpaCloudProviderDao
 * @author Manuel Eduardo Vieda Salomon  (mail@manuelvieda.com)
 * @version	1.0
 * @since	1.0
 */
@Local
public interface CloudProviderDao {
	
	/**
	 * Returns a list with all the Cloud Providers registered on the system
	 * @return List with all Cloud Providers
	 */
	public List<CloudProvider> getCloudProvidersList();
	
	/**
	 * Returns the Cloud Provider intefied with the id
	 * @param id - The id of the Cloud Provider
	 * @return The cloud provider with id equals to <code>id</code>. Null for invalid or not registered id's.
	 */
	public CloudProvider findCloudProvider(int id);

}
