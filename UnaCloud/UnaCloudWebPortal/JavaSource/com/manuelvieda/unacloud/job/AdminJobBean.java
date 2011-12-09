/**
 * UnaCloud - High Performance Computing on the Cloud
 * Manuel Eduardo Vieda Salomón (mail@manuelvieda.com)
 *
 * Creation: 29/11/2011
 * Project: UnaCloudWebPortal
 * Package: com.manuelvieda.unacloud.job
 * File:    AdminJobBean.java
 */
package com.manuelvieda.unacloud.job;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.manuelvieda.unacloud.beans.user.UserBeanLocal;
import com.manuelvieda.unacloud.entities.general.Job;
import com.manuelvieda.unacloud.generic.GenericBackingBean;
import com.manuelvieda.unacloud.repository.services.JobService;

/**
 *
 * @author Manuel Eduardo Vieda Salomon  (mail@manuelvieda.com)
 * @version
 * @since
 */
@ManagedBean (name="adminJobBean")
@ViewScoped
public class AdminJobBean extends GenericBackingBean{
	
	private List<Job> jobs;
	private Job selectedJob;
	
	@EJB
	private UserBeanLocal userBean;
	
	@EJB
	private JobService jobService;
	
	// --------------------------------------------------------------

	/**
	 * 
	 */
	@PostConstruct
	public void init(){
		jobs = jobService.getUserJobs(userBean.getUsername());
	}
	
	/**
	 * 
	 */
	public void selectJob(){
		int idJob = Integer.parseInt(getRequestParam().get("idJob"));
		System.out.println("Seleccionado JOB con id: "+idJob);
		selectedJob = jobService.getJob(idJob);
		System.out.println("Resultado Job: "+selectedJob.getResult());
	}
	
	/**
	 * 
	 */
	public void launchJob(){
		
		if(selectedJob!=null){
			jobService.launchJob(selectedJob);
			
		}
		
	}
	
	/**
	 * 
	 */
	public void stopJob(){
		
	}
	
	/**
	 * 
	 */
	public void deleteJob(){
		
	}
	
	
	// --------------------------------------------------------------


	/**
	 * @return the jobs
	 */
	public List<Job> getJobs() {
		return jobs;
	}

	/**
	 * @param jobs the jobs to set
	 */
	public void setJobs(List<Job> jobs) {
		this.jobs = jobs;
	}

	/**
	 * @return the selectedJob
	 */
	public Job getSelectedJob() {
		return selectedJob;
	}

	/**
	 * @param selectedJob the selectedJob to set
	 */
	public void setSelectedJob(Job selectedJob) {
		this.selectedJob = selectedJob;
	}
		
}
