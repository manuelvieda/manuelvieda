/**
 * UnaCloud - High Performance Computing on the Cloud
 * Manuel Eduardo Vieda Salomón (mail@manuelvieda.com)
 *
 * Creation: 27/10/2011
 * Project: UnaCloudEntities
 * Package: com.manuelvieda.unacloud.repository.dao
 * File:    StateDao.java
 */
package com.manuelvieda.unacloud.repository.dao;

import javax.ejb.Local;

import com.manuelvieda.unacloud.entities.general.State;

/**
 * Interface for Entity Bean implementation class JpaStateDao
 * @author Manuel Eduardo Vieda Salomon  (mail@manuelvieda.com)
 * @version	1.0
 * @since	1.0
 */
@Local
public interface StateDao {
	
	/**
	 * Finds the state by ID
	 * @param id - The id of the state
	 * @return The state object
	 */
	public State find(int id);

}
