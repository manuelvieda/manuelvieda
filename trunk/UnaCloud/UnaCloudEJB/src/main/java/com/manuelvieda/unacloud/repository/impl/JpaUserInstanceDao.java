/**
 * UnaCloud - High Performance Computing on the Cloud
 * Manuel Eduardo Vieda Salomón (mail@manuelvieda.com)
 *
 * Creation: 27/10/2011
 * Project: UnaCloudEntities
 * Package: com.manuelvieda.unacloud.repository.impl
 * File:    JpaUserInstanceDao.java
 */
package com.manuelvieda.unacloud.repository.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityTransaction;

import com.manuelvieda.unacloud.entities.general.State;
import com.manuelvieda.unacloud.entities.general.UserInstance;
import com.manuelvieda.unacloud.repository.constants.Constants;
import com.manuelvieda.unacloud.repository.dao.UserInstanceDao;

/**
 * Session Bean implementation class UserInstanceDao
 * @author Manuel Eduardo Vieda Salomon  (mail@manuelvieda.com)
 * @version	1.0
 * @since	1.0
 */
@Stateless(mappedName="userUserInstanceDao")
public class JpaUserInstanceDao extends JpaGeneric implements UserInstanceDao {
	
	
	/*
	 * (non-Javadoc)
	 * @see com.manuelvieda.unacloud.repository.dao.UserInstanceDao#createUserInstance(com.manuelvieda.unacloud.entities.general.UserInstance)
	 */
	@Override
	public void createUserInstance(UserInstance userInstance) {
		//EntityTransaction entityTransaction = entityManager.getTransaction();
		//entityTransaction.begin();
		//entityManager.joinTransaction();
		//entityManager.flush();
		System.out.println(userInstance.getId());
		System.out.println(userInstance.getInstancetype().getId());
		System.out.println(userInstance.getInstancetype().getName());
		System.out.println(userInstance.getState().getId());
		System.out.println(userInstance.getState().getDescription());
		entityManager.persist(userInstance);
		//entityManager.merge(userInstance);
		System.out.println("persistido UI");
		//entityTransaction.commit();
		//entityManager.flush();
		
	}

	/*
	 * (non-Javadoc)
	 * @see com.manuelvieda.unacloud.repository.dao.UserInstanceDao#getUserInstances(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<UserInstance> getUserInstances(String username) {
		entityManager.flush();
		return entityManager.createNamedQuery(Constants.NQ_USER_INSTANCES_FIND_BY_USER)
				.setParameter("username", username)
				.getResultList();
	}

	/* (non-Javadoc)
	 * @see com.manuelvieda.unacloud.repository.dao.UserInstanceDao#getUserInstancesByCluster(java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<UserInstance> getUserInstancesByCluster(String username, int clusterId) {
		entityManager.flush();
		return entityManager.createNamedQuery(Constants.NQ_USER_INSTANCES_FIND_BY_USER_CLUSTER)
				.setParameter("username", username)
				.setParameter("clusterId", clusterId)
				.getResultList();
		
	}
	

	/*
	 * (non-Javadoc)
	 * @see com.manuelvieda.unacloud.repository.dao.UserInstanceDao#updateInstanteMonitoringInfo(int, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void updateInstanteMonitoringInfo(int id, String idProvider, int status, String publicDns){
		
		entityManager.joinTransaction();
		entityManager.flush();
		//EntityTransaction entityTransaction = entityManager.getTransaction();
		//entityTransaction.begin();
		
		UserInstance instance = entityManager.find(UserInstance.class, id);
		
		if(idProvider!=null)
			instance.setIdentifier(idProvider);
		
		State state = entityManager.find(State.class, status);
		if(state!=null)
			instance.setState(state);
		
		if(publicDns!=null)
			instance.setDnsName(publicDns);
		
		entityManager.merge(instance);
		entityManager.flush();
		
		//entityTransaction.commit();
	}

	/* (non-Javadoc)
	 * @see com.manuelvieda.unacloud.repository.dao.UserInstanceDao#getsUserInstance(int)
	 */
	@Override
	public UserInstance getsUserInstance(int id) {
		return entityManager.find(UserInstance.class, id);
	}

}
