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

import java.sql.Timestamp;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.manuelvieda.unacloud.entities.general.Application;
import com.manuelvieda.unacloud.entities.general.ApplicationParameter;
import com.manuelvieda.unacloud.entities.general.Cluster;
import com.manuelvieda.unacloud.entities.general.Job;
import com.manuelvieda.unacloud.entities.general.UserInstance;
import com.manuelvieda.unacloud.repository.dao.ApplicationDao;
import com.manuelvieda.unacloud.repository.dao.ApplicationParameterDao;
import com.manuelvieda.unacloud.repository.dao.JobDao;
import com.manuelvieda.unacloud.repository.dao.StateDao;
import com.manuelvieda.unacloud.repository.dao.UserDao;
import com.manuelvieda.unacloud.repository.constants.Constants;

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
	
	@EJB
	private JobDao jobDao;
	
	@EJB
	private StateDao stateDao;
	
	@EJB
	private UserDao userDao;
	
	/**
	 * 
	 * @param name
	 * @param description
	 * @param application
	 * @param cluster
	 * @param instance
	 * @param parameters
	 */
	public void createJob(String name, String description, int application, Cluster cluster, UserInstance userinstance, String parameters, String user){
		
		
		Job job = new Job();
		
		job.setApplication(getApplication(application));
		job.setCluster(cluster);
		job.setUserinstance(userinstance);
		job.setCreationTime(new Timestamp(System.currentTimeMillis()));
		
		job.setDescription(description);
		job.setName(name);
		job.setParameters(parameters);
		job.setState(stateDao.find(Constants.STATE_JOB_CREATED));
		job.setUser(userDao.find(user));
		
		jobDao.create(job);
		
		
	}
	
	
	/**
	 * 
	 * @return
	 */
	public List<Application> getAllApplications(){
		return applicationDao.getAllApplications();
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public Application getApplication(int id){
		return applicationDao.find(id);
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
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public Job getJob(int id){
		return jobDao.find(id);
	}
	
	/**
	 * 
	 * @param username
	 * @return
	 */
	public List<Job> getUserJobs(String username){
		return jobDao.findByUser(username);
	}

}
