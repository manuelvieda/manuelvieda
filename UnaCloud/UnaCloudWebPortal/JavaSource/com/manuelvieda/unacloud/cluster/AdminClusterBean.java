/**
 * UnaCloud - High Performance Computing on the Cloud
 * Manuel Eduardo Vieda Salomón (mail@manuelvieda.com)
 *
 * Creation: 28/10/2011
 * Project: UnaCloudWebPortal
 * Package: com.manuelvieda.unacloud.cluster
 * File:    AdminClusterBean.java
 */
package com.manuelvieda.unacloud.cluster;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.commons.collections.CollectionUtils;

import com.manuelvieda.unacloud.beans.user.UserBeanLocal;
import com.manuelvieda.unacloud.entities.Cluster;
import com.manuelvieda.unacloud.entities.Instance;
import com.manuelvieda.unacloud.entities.general.UserInstance;
import com.manuelvieda.unacloud.repository.services.ClusterService;

/**
 * This backing bean defines
 * @author Manuel Eduardo Vieda Salomon  (mail@manuelvieda.com)
 * @version	1.0
 * @since	1.0
 */
@ManagedBean (name="adminClusterBean")
@ViewScoped
public class AdminClusterBean {
	
	@EJB
	private ClusterService clusterService;
	
	@EJB
	private UserBeanLocal userBean;
	
	private List<Cluster> userClustersLst;
	private List<Instance> userClustersInstancesLst;
	
	private Cluster selectedCluster;
	
	
	// --------------------------------------------------------------

	@PostConstruct
	public void init(){
		selectedCluster = getUserClustersLst().get(0); 
	}
	
	public String turnOn(){
		
		for(int i=0; i<15; i++){
			System.out.println("Prueba de inicio de maquinas.... "+i);
		}
		System.out.println("Iniciando Cluster en AMAZON EC2");
		return "";
	}
	
	/**
	 * @return the selectedCluster
	 */
	public Cluster getSelectedCluster() {
		return selectedCluster;
	}

	/**
	 * @param selectedCluster the selectedCluster to set
	 */
	public void setSelectedCluster(Cluster selectedCluster) {
		this.selectedCluster = selectedCluster;
	}

	/**
	 * @return the userClustersLst
	 */
	public List<Cluster> getUserClustersLst() {
		
		if(CollectionUtils.isEmpty(userClustersLst)){
			userClustersLst = new ArrayList<Cluster>();
			List<com.manuelvieda.unacloud.entities.general.Cluster> clusterUsuario = clusterService.getUserClusters(userBean.getUsername());
			for (com.manuelvieda.unacloud.entities.general.Cluster cluster : clusterUsuario) {
				Cluster clusterPortal = new Cluster();
				clusterPortal.setName(cluster.getName());
				clusterPortal.setDescription(cluster.getDescription());
				clusterPortal.setState(cluster.getStateBean().getDescription());
				clusterPortal.setIdState(cluster.getStateBean().getId());
				userClustersLst.add(clusterPortal);
			}
		}
		return userClustersLst;
	}

	/**
	 * @param userClustersLst the userClustersLst to set
	 */
	public void setUserClustersLst(List<Cluster> userClustersLst) {
		this.userClustersLst = userClustersLst;
	}
	
	/**
	 * @return the userClustersInstancesLst
	 */
	public List<Instance> getUserClustersInstancesLst() {
		
		if(CollectionUtils.isEmpty(userClustersInstancesLst)){
			
			System.out.println("Obteniendo instancias de usuario en cluster...");
			
			userClustersInstancesLst = new ArrayList<Instance>();
			List<UserInstance> usrInst = clusterService.getUserInstances(userBean.getUsername());
			for (UserInstance userInstance : usrInst) {
				Instance instan = new Instance();
				instan.setId(userInstance.getId());
				instan.setProvider(userInstance.getClusterBean().getName());
				instan.setInstanceType(userInstance.getInstancetype().getName());
				instan.setPrice(userInstance.getInstancetype().getPrice());
				System.out.println("Id: "+instan.getId()+"--"+userInstance.getId()+"  // Cluster "+userInstance.getClusterBean().getName());
				userClustersInstancesLst.add(instan);
				
			}
			
		}
		return userClustersInstancesLst;
	}

	/**
	 * @param userClustersInstancesLst the userClustersInstancesLst to set
	 */
	public void setUserClustersInstancesLst(List<Instance> userClustersInstancesLst) {
		this.userClustersInstancesLst = userClustersInstancesLst;
	}
	
}
