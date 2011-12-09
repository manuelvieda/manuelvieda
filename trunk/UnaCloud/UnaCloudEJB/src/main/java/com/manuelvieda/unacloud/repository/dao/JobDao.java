/**
 * UnaCloud - High Performance Computing on the Cloud
 * Manuel Eduardo Vieda Salomón (mail@manuelvieda.com)
 *
 * Creation: 27/10/2011
 * Project: UnaCloudEntities
 * Package: com.manuelvieda.unacloud.repository.dao
 * File:    JobDao.java
 */
package com.manuelvieda.unacloud.repository.dao;

import java.util.List;

import javax.ejb.Local;

import com.manuelvieda.unacloud.entities.general.Job;
import com.manuelvieda.unacloud.entities.general.State;

/**
 * Interface for Entity Bean implementation class JpaJobDao	
 * @author Manuel Eduardo Vieda Salomon  (mail@manuelvieda.com)
 * @version	1.0
 * @since	1.0
 */
@Local
public interface JobDao {
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public Job find(int id);
	
	/**
	 * 
	 * @param username
	 * @return
	 */
	public List<Job> findByUser(String username);

	/**
	 * @param job
	 */
	public void create(Job job);
	
	
	/**
	 * 
	 * @param id
	 * @param result
	 */
	public void updateResult(int id, String result, State state);
	
	/**
	 * 
	 * @param id
	 * @param state
	 */
	public void updateSend(int id, State state);

}
