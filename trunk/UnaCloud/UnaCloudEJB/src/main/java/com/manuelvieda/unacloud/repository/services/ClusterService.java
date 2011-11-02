/**
 * UnaCloud - High Performance Computing on the Cloud
 * Manuel Eduardo Vieda Salomón (mail@manuelvieda.com)
 *
 * Creation: 27/10/2011
 * Project: UnaCloudEJB
 * Package: com.manuelvieda.unacloud.repository.services
 * File:    ClusterService.java
 */
package com.manuelvieda.unacloud.repository.services;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.commons.lang3.StringUtils;

import com.manuelvieda.unacloud.entities.general.Cluster;
import com.manuelvieda.unacloud.entities.general.InstanceType;
import com.manuelvieda.unacloud.entities.general.State;
import com.manuelvieda.unacloud.entities.general.User;
import com.manuelvieda.unacloud.entities.general.UserInstance;
import com.manuelvieda.unacloud.repository.constants.Constants;
import com.manuelvieda.unacloud.repository.dao.ClusterDao;
import com.manuelvieda.unacloud.repository.dao.InstanceTypeDao;
import com.manuelvieda.unacloud.repository.dao.StateDao;
import com.manuelvieda.unacloud.repository.dao.UserDao;
import com.manuelvieda.unacloud.repository.dao.UserInstanceDao;


/**
 * Session Bean implementation class ClusterService
 * @author Manuel Eduardo Vieda Salomon  (mail@manuelvieda.com)
 * @version	1.0
 * @since	1.0
 */
@LocalBean
@Stateless
public class ClusterService {
	
	@EJB
	private UserDao userDao;
	
	@EJB
	private StateDao stateDao;
	
	@EJB
	private ClusterDao clusterDao;
	
	@EJB
	private InstanceTypeDao instanceTypeDao;
	
	@EJB
	private UserInstanceDao userInstanceDao;
	
	
	/**
	 * 
	 * @param idOwner
	 * @param name
	 * @param description
	 * @param idState
	 * @param keyInstancesMap
	 */
	public void createCluster(String usernameOwner, String name, String description, int idState,  Map<Integer, Integer> keyInstancesMap){
		
		if(StringUtils.isNotBlank(name)){
			
			System.out.println("----");
			
			// Get the owner's user object
			User owner = userDao.find(usernameOwner);
			
			System.out.println("---- Owner");
			if(owner!=null){
				State stateCluster = stateDao.find(idState);
				System.out.println("----State");
				
				if(stateCluster!=null){
					System.out.println("---- asd");
					Cluster cluster = new Cluster();
					cluster.setUser(owner);
					cluster.setName(name);
					cluster.setDescription(description);
					cluster.setStateBean(stateCluster);
					clusterDao.createCluster(cluster);
					
					// Asign instances to the cluster
					State stateInstance = stateDao.find(Constants.STATE_USER_INSTANCE_ID_STANDBY);
					Set<Entry<Integer, Integer>> setKeyInstanceMap = keyInstancesMap.entrySet();
					for (Entry<Integer, Integer> entry : setKeyInstanceMap) {
						
						InstanceType instanceType = instanceTypeDao.find(entry.getKey());
						if(instanceType!=null){
							
							System.out.println("Agregando instancia de usuario...");
							
							int quantity = entry.getValue();
							for(int i = 0; i<quantity; i++){
								// A valid instance type
								UserInstance userInstance = new UserInstance();
								userInstance.setClusterBean(cluster);
								userInstance.setInstancetype(instanceType);
								userInstance.setStateBean(stateInstance);
								System.out.println("Id: "+userInstance.getId()+"  // Cluster "+userInstance.getClusterBean());
								userInstanceDao.createUserInstance(userInstance);
							}
						}
					}
				}
			}
			
		}
	}
	
	
	/**
	 * 
	 * @param username
	 * @return
	 */
	public List<Cluster> getUserClusters(String username){
		return clusterDao.findByUser(username);
	}
	
	
	public List<UserInstance> getUserInstances(String username){
		return userInstanceDao.getUserInstances(username);
	}

}
