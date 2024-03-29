/**
 * UnaCloud - High Performance Computing on the Cloud
 * Manuel Eduardo Vieda Salom�n (mail@manuelvieda.com)
 *
 * Creation: 27/10/2011
 * Project: UnaCloudEntities
 * Package: com.manuelvieda.unacloud.repository.dao
 * File:    ApplicationParameter.java
 */
package com.manuelvieda.unacloud.repository.dao;

import java.util.List;

import javax.ejb.Local;

import com.manuelvieda.unacloud.entities.general.ApplicationParameter;

/**
 * Interface for Entity Bean implementation class JpaApplicationParameter
 * @author Manuel Eduardo Vieda Salomon  (mail@manuelvieda.com)
 * @version	1.0
 * @since	1.0
 */
@Local
public interface ApplicationParameterDao {
	
	/**
	 * 
	 * @param idAplication
	 * @return
	 */
	public List<ApplicationParameter> getApplicationParameters(int idAplication);

	/**
	 * @param id
	 * @return
	 */
	public ApplicationParameter find(int id);

}
