/**
 * UnaCloud - High Performance Computing on the Cloud
 * Manuel Eduardo Vieda Salomón (mail@manuelvieda.com)
 *
 * Creation: 27/10/2011
 * Project: UnaCloudEntities
 * Package: com.manuelvieda.unacloud.repository.dao
 * File:    ApplicationDao.java
 */
package com.manuelvieda.unacloud.repository.dao;

import java.util.List;

import javax.ejb.Local;

import com.manuelvieda.unacloud.entities.general.Application;

/**
 * Interface for Entity Bean implementation class JpaApplicationDao
 * @author Manuel Eduardo Vieda Salomon  (mail@manuelvieda.com)
 * @version	1.0
 * @since	1.0
 */
@Local
public interface ApplicationDao {
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public Application find(int id);
	
	
	/**
	 * 
	 * @return
	 */
	public List<Application> getAllApplications();
	
}
