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

import java.sql.Timestamp;
import java.util.List;

import javax.ejb.Stateless;

import com.manuelvieda.unacloud.entities.general.Job;
import com.manuelvieda.unacloud.entities.general.State;
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
		//return (Job) entityManager.createNamedQuery("job.findById").setParameter("id", id).getSingleResult();
		entityManager.flush();
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
		System.out.println("Creando trabajo");
		
		//EntityTransaction entityTransaction = entityManager.getTransaction();
		//entityTransaction.begin();
		entityManager.flush();
		entityManager.persist(job);
		entityManager.merge(job);
		entityManager.flush();
		//entityTransaction.commit();
		System.out.println("Trabajo creado");
		
	}

	/* (non-Javadoc)
	 * @see com.manuelvieda.unacloud.repository.dao.JobDao#updateResult(int, java.lang.String)
	 */
	@Override
	public void updateResult(int id, String result, State state) {
		
		System.out.println("---> Almacenando resultado de trabajo! "+id+"  con resultado "+result+" y estado "+state.getDescription());
		//EntityTransaction entityTransaction = entityManager.getTransaction();
		//entityTransaction.begin();
		entityManager.joinTransaction();
		entityManager.flush();
		
		int resultado = entityManager.createNamedQuery(Constants.NQ_JOB_UPDATE_RESULT)
			.setParameter("idJob", id)
			.setParameter("state", state)
			.setParameter("finishTime", new Timestamp(System.currentTimeMillis()))
			.setParameter("result", result)
			.executeUpdate();
		//ent
		System.out.println("---> Finalizao actualizacion con resultado "+resultado);
		//entityTransaction.commit();
		Job job = entityManager.find(Job.class, id);
		job.setState(state);
		job.setResult(result);
		job.setFinishTime(new Timestamp(System.currentTimeMillis()));
		entityManager.merge(job);
		entityManager.flush();
		System.out.println("---> Finalizo Transaccion");
	}

	/* (non-Javadoc)
	 * @see com.manuelvieda.unacloud.repository.dao.JobDao#updateSend(int, com.manuelvieda.unacloud.entities.general.State)
	 */
	@Override
	public void updateSend(int id, State state) {
		Job job = find(id);
		job.setState(state);
		entityManager.persist(job);
		entityManager.flush();
		
	}

}
