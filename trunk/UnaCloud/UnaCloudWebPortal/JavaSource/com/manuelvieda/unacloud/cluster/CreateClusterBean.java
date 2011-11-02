/**
 * UnaCloud - High Performance Computing on the Cloud
 * Manuel Eduardo Vieda Salomón (mail@manuelvieda.com)
 *
 * Creation: 01/10/2011
 * Project:  UnaCloudWebPortal
 * Package:  com.manuelvieda.unacloud.cluster
 * File:     CreateClusterBean.java
 */
package com.manuelvieda.unacloud.cluster;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import com.manuelvieda.unacloud.beans.user.UserBeanLocal;
import com.manuelvieda.unacloud.entities.Instance;
import com.manuelvieda.unacloud.entities.general.CloudProvider;
import com.manuelvieda.unacloud.entities.general.InstanceType;
import com.manuelvieda.unacloud.repository.constants.Constants;
import com.manuelvieda.unacloud.repository.services.CloudProviderService;
import com.manuelvieda.unacloud.repository.services.ClusterService;

/**
* This backing bean defines how the system creates a new cluster for a specific user.
* 
* @author  Manuel Eduardo Vieda
* @version 1.0
* @since   1.0
*/
@ManagedBean (name="createClusterBean")
@ViewScoped
public class CreateClusterBean {
	
	@EJB
	private CloudProviderService cloudProviderService;
	
	@EJB
	private ClusterService clusterService;
	
	/**
	 * Reference to the EJB that contains and controlls the user information
	 */
	@EJB
	private UserBeanLocal userBean;
	
	
	
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
	
	
	private int provider;
	private int instanceType;
	private int quantity;
	
	private List<SelectItem> providerLst;
	private List<SelectItem> instanceTypeLst;
	private List<Instance> instances;
	
	
	// -----------------------------------------------------------
	
	/**
	 * Default constructor
	 */
	public CreateClusterBean() {
		super();
		instances = new ArrayList<Instance>();
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
	 * @return the provider
	 */
	public int getProvider() {
		return provider;
	}

	/**
	 * @param provider the provider to set
	 */
	public void setProvider(int provider) {
		this.provider = provider;
	}

	/**
	 * @return the instanceType
	 */
	public int getInstanceType() {
		return instanceType;
	}

	/**
	 * @param instanceType the instanceType to set
	 */
	public void setInstanceType(int instanceType) {
		this.instanceType = instanceType;
	}

	/**
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return the providerLst
	 */
	public List<SelectItem> getProviderLst() {
		if(CollectionUtils.isEmpty(providerLst)){
			
			// Get providers
			List<CloudProvider> cloudProviders = cloudProviderService.getAllCloudProviders();
			
			// Create the list for interface
			providerLst = new ArrayList<SelectItem>();
			for (CloudProvider cloudProvider : cloudProviders) {
				providerLst.add(new SelectItem(cloudProvider.getId(), cloudProvider.getName(), cloudProvider.getDescription()));
			}
		}
		return providerLst;
	}
	
	/**
	 * @return the instaceTypeLst
	 */
	public List<SelectItem> getInstanceTypeLst() {
		if(provider>0){
			// Get Instance types
			List<InstanceType> instanceTypes = cloudProviderService.getAllInstanceTypeFromProvider(provider);
			
			// Create the list for interface
			instanceTypeLst = new ArrayList<SelectItem>();
			for (InstanceType instanceType : instanceTypes) {
				instanceTypeLst.add(new SelectItem(instanceType.getId(), instanceType.getName(), instanceType.getDescription()));
			}
		}
		return instanceTypeLst;
	}
	
	
	/**
	 * @return True to disable Instance type selector.
	 */
	public boolean isHideInstancesTypes() {
		return provider>0 ? false : true;
	}
	
	/**
	 * @return True to disable Instances Quentity input area.
	 */
	public boolean isHideInstancesQuantity(){
		return instanceType>0 ? false: true;
	}

	
	
	// -----------------------------------------------------------
	
	

	/**
	 * Adds an instance to the current Instances of the cluster
	 */
	public void addInstance(){
		boolean alredyInList = false;
		for (Instance instance : instances) {
			if(instance.getIdInstanceType()==instanceType){
				instance.setQuantity(quantity+instance.getQuantity());
				alredyInList = true;
				break;
			}
			
		}
		
		if(!alredyInList){
			InstanceType insType= cloudProviderService.getInstanceType(instanceType);
			if(insType!=null){
				Instance instance = new Instance();
				instance.setInstanceType(insType.getName());
				instance.setProvider(insType.getCloudprovider().getName());			
				instance.setIdProvider(provider);
				instance.setIdInstanceType(instanceType);
				instance.setQuantity(quantity);
				instances.add(instance);
				
				provider = 0;
				instanceType = 0;
				quantity = 0;
			}
		}
	}
	
	/**
	 * Creates the Cluster of the user on the dataBase
	 */
	public void create(){
		
		System.out.println("--->> Creando cluster");
		if(CollectionUtils.isNotEmpty(instances)){
			
			System.out.println("--->> Tiene instancias");
			
			Map<Integer, Integer> instancesMap = new HashMap<Integer, Integer>();
			
			for (Instance inst : instances) {
				System.out.println("--->> Adicionando instancias");
				instancesMap.put(inst.getIdInstanceType(), inst.getQuantity());
			}
			
			System.out.println("--->> Creado hashmap");
			clusterService.createCluster(userBean.getUsername(), name, description, Constants.STATE_CLUSTER_ID_STANDBY, instancesMap);
			
			System.out.println("--->> Creado cluster en base de dato");
		}
	}


	/**
	 * @return the instances
	 */
	public List<Instance> getInstances() {
		return instances;
	}


	/**
	 * @param instances the instances to set
	 */
	public void setInstances(List<Instance> instances) {
		this.instances = instances;
	}
	
}
