/**
 * UnaCloud - High Performance Computing on the Cloud
 * Manuel Eduardo Vieda Salomón (mail@manuelvieda.com)
 *
 * Creation: 27/10/2011
 * Project: UnaCloudEntities
 * Package: com.manuelvieda.unacloud.repository.impl
 * File:    JpaJobDao.java
 */
package com.manuelvieda.unacloud.repository.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityTransaction;

import com.manuelvieda.unacloud.entities.general.Job;
import com.manuelvieda.unacloud.repository.dao.JobDao;
import com.manuelvieda.unacloud.repository.constants.Constants;

/**
 * Session Bean implementation class JobDao
 * @author Manuel Eduardo Vieda Salomon  (mail@manuelvieda.com)
 * @version	1.0
 * @since	1.0
 */
@Stateless(mappedName="jobDao")
public class JpaJobDao extends JpaGeneric implements JobDao {

	/* (non-Javadoc)
	 * @see com.manuelvieda.unacloud.repository.dao.JobDao#find(int)
	 */
	@Override
	public Job find(int id) {
		return entityManager.find(Job.class, id);
	}

	/* (non-Javadoc)
	 * @see com.manuelvieda.unacloud.repository.dao.JobDao#findByUser(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Job> findByUser(String username) {
		return entityManager.createNamedQuery(Constants.NQ_JOB_FIND_BY_USER)
				.setParameter("username", username)
				.getResultList();
	}

	/* (non-Javadoc)
	 * @see com.manuelvieda.unacloud.repository.dao.JobDao#create(com.manuelvieda.unacloud.entities.general.Job)
	 */
	@Override
	public void create(Job job) {
		
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		entityManager.persist(job);
		entityTransaction.commit();
		
	}

}
