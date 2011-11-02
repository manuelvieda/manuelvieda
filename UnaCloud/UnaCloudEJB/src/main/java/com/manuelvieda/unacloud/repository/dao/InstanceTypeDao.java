/**
 * UnaCloud - High Performance Computing on the Cloud
 * Manuel Eduardo Vieda Salomón (mail@manuelvieda.com)
 *
 * Creation: 27/10/2011
 * Project: UnaCloudEntities
 * Package: com.manuelvieda.unacloud.repository.dao
 * File:    InstanceTypeDao.java
 */
package com.manuelvieda.unacloud.repository.dao;

import java.util.List;

import javax.ejb.Local;

import com.manuelvieda.unacloud.entities.general.InstanceType;

/**
 * Interface for Entity Bean implementation class  JpaInstanceTypeDao
 * @author Manuel Eduardo Vieda Salomon  (mail@manuelvieda.com)
 * @version	1.0
 * @since	1.0
 */
@Local
public interface InstanceTypeDao {
	
	/**
	 * Returns de Instance type identified with the provided <code>id</code>
	 * @param id - The identifier of the Instance Type
	 * @return The object of the Instance type if existes, null otherwise
	 */
	public InstanceType find(int id);
	
	/**
	 * Return a list with all the Instance Type from a specific Cloud Provider
	 * @param idProvider - The id of the Cloud Provider
	 * @return
	 */
	public List<InstanceType> getAllInstanceTypeFromCloudProvider(int idProvider);

}
