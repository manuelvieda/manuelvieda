/**
 * UnaCloud - High Performance Computing on the Cloud
 * Manuel Eduardo Vieda Salomón (mail@manuelvieda.com)
 *
 * Creation: 23/11/2011
 * Project: UnaCloudEJB
 * Package: com.manuelvieda.unacloud.repository.services
 * File:    JobService.java
 */
package com.manuelvieda.unacloud.repository.services;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.manuelvieda.unacloud.entities.general.Application;
import com.manuelvieda.unacloud.entities.general.ApplicationParameter;
import com.manuelvieda.unacloud.repository.dao.ApplicationDao;
import com.manuelvieda.unacloud.repository.dao.ApplicationParameterDao;

/**
 * Session Bean implementation class JobService
 * @author Manuel Eduardo Vieda Salomon  (mail@manuelvieda.com)
 * @version	1.0
 * @since	1.0
 */
@LocalBean
@Stateless
public class JobService {
	
	@EJB
	private ApplicationDao applicationDao;
	
	@EJB
	private ApplicationParameterDao applicationParameterDao;
	
	
	/**
	 * 
	 * @return
	 */
	public List<Application> getAllApplications(){
		return applicationDao.getAllApplications();
	}
	
	/**
	 * 
	 * @param idApplication
	 * @return
	 */
	public List<ApplicationParameter> getApplicationParameters(int idApplication){
		return applicationParameterDao.getApplicationParameters(idApplication);
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public ApplicationParameter getApplicationParameter(int id){
		return applicationParameterDao.find(id);
	}

}
