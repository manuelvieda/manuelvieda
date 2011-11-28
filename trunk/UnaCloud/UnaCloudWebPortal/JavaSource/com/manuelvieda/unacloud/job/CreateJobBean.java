/**
 * UnaCloud - High Performance Computing on the Cloud
 * Manuel Eduardo Vieda Salomón (mail@manuelvieda.com)
 *
 * Creation: 22/11/2011
 * Project: UnaCloudWebPortal
 * Package: com.manuelvieda.unacloud.job
 * File:    CreateJobBean.java
 */
package com.manuelvieda.unacloud.job;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.apache.commons.collections.CollectionUtils;
import org.eclipse.persistence.annotations.ValuePartition;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import com.manuelvieda.unacloud.beans.user.UserBeanLocal;
import com.manuelvieda.unacloud.entities.general.Application;
import com.manuelvieda.unacloud.entities.general.ApplicationParameter;
import com.manuelvieda.unacloud.entities.general.Cluster;
import com.manuelvieda.unacloud.entities.general.UserInstance;
import com.manuelvieda.unacloud.repository.services.ClusterService;
import com.manuelvieda.unacloud.repository.services.JobService;

/**
 *
 * @author Manuel Eduardo Vieda Salomon  (mail@manuelvieda.com)
 * @version 1.0
 * @since	1.0
 */
@ManagedBean (name="createJobBean")
@ViewScoped
public class CreateJobBean {
	
	/**
	 * Reference to the EJB that contains and controlls the user information
	 */
	@EJB
	private UserBeanLocal userBean;
	
	@EJB
	private JobService jobService;
	
	@EJB
	private ClusterService clusterService;
	
	
	private int application;
	private int applicationParameter;
	
	
	
	private List<SelectItem> applicationLst;
	private List<SelectItem> applicationParameterLst;
	private List<SelectItem> clusterLst;
	private List<SelectItem> instanceLst;
	
	private boolean disableButtons = true;
	
	private List<com.manuelvieda.unacloud.entities.ApplicationParameter> definedApplicationParameters;
	
	private String parameterValue;
	

	
	// -----------------------------------------------------------
	
	
	/**
	 * The name of the cluster. Used by the user to identify it.
	 */
	@NotEmpty
	@NotBlank
	@Length(min=3,max=20)
	private String name;
	
	/**
	 * A brief description of the cluster. Used by the user to identify it.
	 */
	@NotEmpty
	@NotBlank
	@Length(min=3,max=200)
	private String description;
	
	/**
	 * 
	 */
	@Min(value = 1, message="A cluster and a instances must be selected")
    @Max(value = 99, message="A cluster and a instances must be selected")
	private int cluster;
	
	/**
	 * 
	 */
	@Min(value = 1, message="A cluster and a instances must be selected")
    @Max(value = 99, message="A cluster and a instances must be selected")
	private int instance;
	
	// -----------------------------------------------------------
	
	/**
	 * Default constructor
	 */
	public CreateJobBean() {
		super();
		disableButtons = true;
	}
	
	
	/**
	 * 
	 */
	public void addParameter(){
		
		System.out.println("---> Definiendo parametro");
		
		if(definedApplicationParameters==null)
			definedApplicationParameters = new ArrayList<>();
			
		ApplicationParameter appParam = jobService.getApplicationParameter(applicationParameter);
		
		com.manuelvieda.unacloud.entities.ApplicationParameter ap = new com.manuelvieda.unacloud.entities.ApplicationParameter();
		ap.setId(definedApplicationParameters.size()+1);
		ap.setName(appParam.getName());
		ap.setDescription(appParam.getDescription());
		ap.setType(appParam.getType());
		ap.setValue(this.parameterValue);
		
		definedApplicationParameters.add(ap);
		
		application = 0;
		applicationParameter = 0;
		parameterValue = "";
		
		System.out.println("--> Agregado parametro: "+definedApplicationParameters.size());
		
		
	}
	
	
	/**
	 * 
	 */
	public void createJob(){
		
	}
	
	
	/**
	 * 
	 */
	public void createLunchJob(){
		
	}
	
	
	/**
	 * 
	 */
	public void cancelJob(){
		disableButtons = true;
		application = 0;
		applicationParameter = 0;
		parameterValue = "";
		definedApplicationParameters = new ArrayList<>();
		cluster = 0;
		instance = 0;
		name = null;
		description = null;
	}
	
	// -----------------------------------------------------------


	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * @return the parameterValue
	 */
	public String getParameterValue() {
		return parameterValue;
	}


	/**
	 * @param parameterValue the parameterValue to set
	 */
	public void setParameterValue(String parameterValue) {
		this.parameterValue = parameterValue;
	}


	/**
	 * @return the application
	 */
	public int getApplication() {
		return application;
	}


	/**
	 * @param application the application to set
	 */
	public void setApplication(int application) {
		this.application = application;
	}


	/**
	 * @return the applicationParameter
	 */
	public int getApplicationParameter() {
		return applicationParameter;
	}


	/**
	 * @param applicationParameter the applicationParameter to set
	 */
	public void setApplicationParameter(int applicationParameter) {
		this.applicationParameter = applicationParameter;
	}


	/**
	 * @return the applicationLst
	 */
	public List<SelectItem> getApplicationLst() {
		
		if(CollectionUtils.isEmpty(applicationLst)){
			applicationLst = new ArrayList<>();
			List<Application> applications = jobService.getAllApplications();
			for (Application application : applications) {
				applicationLst.add(new SelectItem(application.getId(), application.getName()));
			}
		}
		
		return applicationLst;
	}


	/**
	 * @param applicationLst the applicationLst to set
	 */
	public void setApplicationLst(List<SelectItem> applicationLst) {
		System.out.println("----> SETSelectedApplication");
		this.applicationLst = applicationLst;
	}


	/**
	 * @return the applicationParameterLst
	 */
	public List<SelectItem> getApplicationParameterLst() {
		
		if(getApplication()>0){
			applicationParameterLst = new ArrayList<>();
			List<ApplicationParameter> params = jobService.getApplicationParameters(getApplication());
			for (ApplicationParameter applicationParameter : params) {
				applicationParameterLst.add(new SelectItem(applicationParameter.getId(), applicationParameter.getName()+" "+( applicationParameter.getMandatory() ? "(Obligatorio)":"")));
			}
		}
		
		return applicationParameterLst;
	}


	/**
	 * @param applicationParameterLst the applicationParameterLst to set
	 */
	public void setApplicationParameterLst(
			List<SelectItem> applicationParameterLst) {
		this.applicationParameterLst = applicationParameterLst;
	}


	/**
	 * @return the definedApplicationParameters
	 */
	public List<com.manuelvieda.unacloud.entities.ApplicationParameter> getDefinedApplicationParameters() {
		return definedApplicationParameters;
	}


	/**
	 * @param definedApplicationParameters the definedApplicationParameters to set
	 */
	public void setDefinedApplicationParameters(
			List<com.manuelvieda.unacloud.entities.ApplicationParameter> definedApplicationParameters) {
		this.definedApplicationParameters = definedApplicationParameters;
	}


	/**
	 * @return the cluster
	 */
	public int getCluster() {
		return cluster;
	}


	/**
	 * @param cluster the cluster to set
	 */
	public void setCluster(int cluster) {
		this.cluster = cluster;
	}


	/**
	 * @return the instance
	 */
	public int getInstance() {
		return instance;
	}


	/**
	 * @param instance the instance to set
	 */
	public void setInstance(int instance) {
		this.instance = instance;
	}


	/**
	 * @return the clusterLst
	 */
	public List<SelectItem> getClusterLst() {
		
		if(CollectionUtils.isEmpty(clusterLst)){
			
			clusterLst = new ArrayList<>();	
			List<Cluster> clusters = clusterService.getUserClusters(userBean.getUsername());
			for (Cluster cluster : clusters) {
				clusterLst.add(new SelectItem(cluster.getId(), cluster.getName()));
			}
			
		}
			
		return clusterLst;
	}


	/**
	 * @param clusterLst the clusterLst to set
	 */
	public void setClusterLst(List<SelectItem> clusterLst) {
		this.clusterLst = clusterLst;
	}


	/**
	 * @return the instanceLst
	 */
	public List<SelectItem> getInstanceLst() {
		
		if(cluster>0){
			instanceLst = new ArrayList<>();
			
			List<UserInstance> instances = clusterService.getUserInstancesByCluster(userBean.getUsername(), cluster);
			for (UserInstance userInstance : instances) {
				instanceLst.add(new SelectItem(
						userInstance.getId(), userInstance.getId()+
						" - "+userInstance.getInstancetype().getName()+
						" ("+userInstance.getInstancetype().getCloudprovider().getName()+")" ));
			}
		}
		
		
		return instanceLst;
	}


	/**
	 * @param instanceLst the instanceLst to set
	 */
	public void setInstanceLst(List<SelectItem> instanceLst) {
		this.instanceLst = instanceLst;
	}


	/**
	 * @return the disableButtons
	 */
	public boolean isDisableButtons() {
		if(application>0)
			disableButtons = false;
		
		return disableButtons;
	}


	/**
	 * @param disableButtons the disableButtons to set
	 */
	public void setDisableButtons(boolean disableButtons) {
		this.disableButtons = disableButtons;
	}
	

}
