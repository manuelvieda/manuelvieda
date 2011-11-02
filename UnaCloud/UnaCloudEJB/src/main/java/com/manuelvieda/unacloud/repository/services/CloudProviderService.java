/**
 * UnaCloud - High Performance Computing on the Cloud
 * Manuel Eduardo Vieda Salomón (mail@manuelvieda.com)
 *
 * Creation: 27/10/2011
 * Project: UnaCloudEJB
 * Package: com.manuelvieda.unacloud.repository.services
 * File:    CloudProviderService.java
 */
package com.manuelvieda.unacloud.repository.services;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.manuelvieda.unacloud.entities.general.CloudProvider;
import com.manuelvieda.unacloud.entities.general.InstanceType;
import com.manuelvieda.unacloud.repository.dao.CloudProviderDao;
import com.manuelvieda.unacloud.repository.dao.InstanceTypeDao;

/**
 * Session Bean implementation class CloudProviderService
 * @author Manuel Eduardo Vieda Salomon  (mail@manuelvieda.com)
 * @version	1.0
 * @since	1.0
 */
@LocalBean
@Stateless
public class CloudProviderService {
	
	@EJB
	private CloudProviderDao cloudProviderDao;
	
	@EJB
	private InstanceTypeDao instanceTypeDao;
	
	
	/**
	 * Returns a list with all the Cloud Providers registered on the system
	 * @return
	 */
	public List<CloudProvider> getAllCloudProviders(){
		return cloudProviderDao.getCloudProvidersList();
	}
	
	
	/**
	 * Returns de Instance type identified with the provided <code>id</code>
	 * @param id - The identifier of the Instance Type
	 * @return The object of the Instance type if existes, null otherwise
	 */
	public InstanceType getInstanceType(int id){
		return instanceTypeDao.find(id);
	}
	
	/**
	 * Return a list with all the Instance Type from a specific Cloud Provider
	 * @param idProvider - The id of the Cloud Provider
	 * @return
	 */
	public List<InstanceType> getAllInstanceTypeFromProvider(int idProvider){
		return instanceTypeDao.getAllInstanceTypeFromCloudProvider(idProvider);
	}

}
