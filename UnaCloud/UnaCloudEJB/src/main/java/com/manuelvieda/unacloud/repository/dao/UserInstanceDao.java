/**
 * UnaCloud - High Performance Computing on the Cloud
 * Manuel Eduardo Vieda Salomón (mail@manuelvieda.com)
 *
 * Creation: 27/10/2011
 * Project: UnaCloudEntities
 * Package: com.manuelvieda.unacloud.repository.dao
 * File:    UserInstanceDao.java
 */
package com.manuelvieda.unacloud.repository.dao;

import java.util.List;

import javax.ejb.Local;

import com.manuelvieda.unacloud.entities.general.UserInstance;

/**
 * Interface for Entity Bean implementation class JpaUserInstanceDao
 * @author Manuel Eduardo Vieda Salomon  (mail@manuelvieda.com)
 * @version	1.0
 * @since	1.0
 */
@Local
public interface UserInstanceDao {
	
	
	/**
	 * 
	 * @param id
	 */
	public UserInstance getsUserInstance(int id);

	/**
	 * 
	 * @param userInstance
	 */
	public void createUserInstance(UserInstance userInstance);
	
	/**
	 * 
	 * @param username
	 */
	public List<UserInstance> getUserInstances(String username);

	/**
	 * @param username
	 * @param clusterId
	 * @return
	 */
	public List<UserInstance> getUserInstancesByCluster(String username, int clusterId);
	
	/**
	 * 
	 * @param id
	 * @param status
	 * @param publicDns
	 */
	public void updateInstanteMonitoringInfo(int id, String idProvider, int status, String publicDns);
	
}
