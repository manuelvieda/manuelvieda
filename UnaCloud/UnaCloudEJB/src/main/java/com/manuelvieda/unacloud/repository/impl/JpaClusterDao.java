/**
 * UnaCloud - High Performance Computing on the Cloud
 * Manuel Eduardo Vieda Salomón (mail@manuelvieda.com)
 *
 * Creation: 27/10/2011
 * Project: UnaCloudEntities
 * Package: com.manuelvieda.unacloud.repository.impl
 * File:    JpaClusterDao.java
 */
package com.manuelvieda.unacloud.repository.impl;

import java.util.List;

import javax.ejb.Stateless;

import com.manuelvieda.unacloud.entities.general.Cluster;
import com.manuelvieda.unacloud.entities.general.State;
import com.manuelvieda.unacloud.entities.general.User;
import com.manuelvieda.unacloud.repository.constants.Constants;
import com.manuelvieda.unacloud.repository.dao.ClusterDao;

/**
 * Session Bean implementation class ClusterDao
 * @author Manuel Eduardo Vieda Salomon  (mail@manuelvieda.com)
 * @version	1.0
 * @since	1.0
 */
@Stateless(mappedName="clusterDao")
public class JpaClusterDao extends JpaGeneric implements ClusterDao {

	/*
	 * (non-Javadoc)
	 * @see com.manuelvieda.unacloud.repository.dao.ClusterDao#createCluster(int, java.lang.String, java.lang.String, int)
	 */
	@Override
	public void createCluster(User user, String name, String description, State state) {
		
		if(user!=null && state!=null){
			Cluster cluster = new Cluster();
			cluster.setName(name);
			cluster.setDescription(description);
			cluster.setUser(user);
			cluster.setStateBean(state);
			createCluster(cluster);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.manuelvieda.unacloud.repository.dao.ClusterDao#createCluster(com.manuelvieda.unacloud.entities.general.Cluster)
	 */
	@Override
	public void createCluster(Cluster cluster) {
		System.out.println("persistiendo Custer");
		entityManager.persist(cluster);
	}

	
	/*
	 * (non-Javadoc)
	 * @see com.manuelvieda.unacloud.repository.dao.ClusterDao#findByUser(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Cluster> findByUser(String username) {
		return entityManager.createNamedQuery(Constants.NQ_CLUSTER_FIND_BY_USER)
					.setParameter("username", username)
					.getResultList();
	}

}
