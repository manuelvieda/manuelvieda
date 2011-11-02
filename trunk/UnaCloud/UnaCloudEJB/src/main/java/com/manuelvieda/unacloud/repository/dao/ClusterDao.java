/**
 * UnaCloud - High Performance Computing on the Cloud
 * Manuel Eduardo Vieda Salomón (mail@manuelvieda.com)
 *
 * Creation: 27/10/2011
 * Project: UnaCloudEntities
 * Package: com.manuelvieda.unacloud.repository.dao
 * File:    ClusterDao.java
 */
package com.manuelvieda.unacloud.repository.dao;

import java.util.List;

import javax.ejb.Local;

import com.manuelvieda.unacloud.entities.general.Cluster;
import com.manuelvieda.unacloud.entities.general.State;
import com.manuelvieda.unacloud.entities.general.User;

/**
 * Interface for Entity Bean implementation class JpaClusterDao
 * @author Manuel Eduardo Vieda Salomon  (mail@manuelvieda.com)
 * @version	1.0
 * @since	1.0
 */
@Local
public interface ClusterDao {
	
	
	/**
	 * 
	 * @param user
	 * @param name
	 * @param description
	 * @param state
	 */
	public void createCluster(User user, String name, String description, State state);
	
	/**
	 * 
	 * @param cluster
	 */
	public void createCluster(Cluster cluster);
	
	/**
	 * 
	 * @param username
	 * @return
	 */
	public List<Cluster> findByUser(String username);

}
