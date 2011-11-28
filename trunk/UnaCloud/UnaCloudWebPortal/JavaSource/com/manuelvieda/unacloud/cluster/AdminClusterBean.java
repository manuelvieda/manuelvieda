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
import com.manuelvieda.unacloud.generic.GenericBackingBean;
import com.manuelvieda.unacloud.provider.ICloudProvider;
import com.manuelvieda.unacloud.repository.services.ClusterService;

/**
 * This backing bean defines
 * @author Manuel Eduardo Vieda Salomon  (mail@manuelvieda.com)
 * @version	1.0
 * @since	1.0
 */
@ManagedBean (name="adminClusterBean")
@ViewScoped
public class AdminClusterBean extends GenericBackingBean{
	
	@EJB
	private ClusterService clusterService;
	
	@EJB
	private UserBeanLocal userBean;
	
	@EJB (name="unaCloudAmazonEC2Bean")
	private ICloudProvider amazonEC2;
	
	
	private List<Cluster> userClustersLst;
	
	private List<Instance> userClustersInstancesLst;
	
	private Cluster selectedCluster;
	
	
	// --------------------------------------------------------------

	@PostConstruct
	public void init(){
	}
	
	/**
	 * 
	 * @return
	 */
	public String turnOn(){
		
		if(selectedCluster!=null){
			com.manuelvieda.unacloud.entities.general.Cluster cluster = clusterService.getCluster(selectedCluster.getIdCluster());
			amazonEC2.turnOnCluster(cluster);
		}
		
		return "";
	}
	
	public String turnOff(){
		com.manuelvieda.unacloud.entities.general.Cluster cluster = clusterService.getCluster(selectedCluster.getIdCluster());
		amazonEC2.turnOffCluster(cluster);
		return "";
	}
	
	/**
	 * 
	 * @return
	 */
	public String selectCluster(){
		
		int idCluster = Integer.parseInt(getRequestParam().get("idCluster"));
		com.manuelvieda.unacloud.entities.general.Cluster cluster = clusterService.getCluster(idCluster);
		if(cluster!=null){
			selectedCluster = new Cluster();
			selectedCluster.setIdCluster(cluster.getId());
			selectedCluster.setName(cluster.getName());
			selectedCluster.setDescription(cluster.getDescription());
			selectedCluster.setState(cluster.getState().getDescription());
			selectedCluster.setIdState(cluster.getState().getId());
			selectedCluster.setTotalInstances(cluster.getUserinstances().size());
		}
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
				clusterPortal.setIdCluster(cluster.getId());
				clusterPortal.setName(cluster.getName());
				clusterPortal.setDescription(cluster.getDescription());
				clusterPortal.setState(cluster.getState().getDescription());
				clusterPortal.setIdState(cluster.getState().getId());
				clusterPortal.setTotalInstances(cluster.getUserinstances().size());
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
		
		
		if(selectedCluster!=null){	
			userClustersInstancesLst = new ArrayList<Instance>();
			List<UserInstance> usrInst = clusterService.getUserInstancesByCluster(userBean.getUsername(), selectedCluster.getIdCluster());
			for (UserInstance userInstance : usrInst) {
				Instance instan = new Instance();
				instan.setId(userInstance.getId());
				instan.setProvider(userInstance.getCluster().getName());
				instan.setInstanceType(userInstance.getInstancetype().getName());
				instan.setPrice(userInstance.getInstancetype().getPrice());
				instan.setStatus(userInstance.getState().getDescription());
				instan.setPublicDNS(userInstance.getDnsName());
				userClustersInstancesLst.add(instan);
				
			}
		}else{
			userClustersInstancesLst = new ArrayList<Instance>();
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
