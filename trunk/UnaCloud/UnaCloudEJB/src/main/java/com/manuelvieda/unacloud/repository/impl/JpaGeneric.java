/**
 * UnaCloud - High Performance Computing on the Cloud
 * Manuel Eduardo Vieda Salomón (mail@manuelvieda.com)
 *
 * Creation: 26/10/2011
 * Project: UnaCloudEntities
 * Package: com.manuelvieda.unacloud.repository.impl
 * File:    JpaGeneric.java
 */
package com.manuelvieda.unacloud.repository.impl;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;

/**
 * Generic implementacion of JPA Services / Clases	
 * @author Manuel Eduardo Vieda Salomon  (mail@manuelvieda.com)
 * @version	1.0
 * @since	1.0
 */
public class JpaGeneric {
	
	@PersistenceUnit(unitName="UnaCloudEntities")
	protected EntityManagerFactory entityManagerFactory;
	
	protected EntityManager entityManager;
	
	// -----------------------------------------------------------------------
	
	/**
	 * Defaul Constructor
	 */
	public JpaGeneric(){
		entityManagerFactory = Persistence.createEntityManagerFactory("UnaCloudEntities");
		entityManager = entityManagerFactory.createEntityManager();
	}
	
	
	// -----------------------------------------------------------------------
	
	
	@PostConstruct
	public void init(){
		entityManager = entityManagerFactory.createEntityManager();
		System.out.println("Crado Entity Manager");
	}
	
	@PreDestroy
	public void destroy(){
		entityManager.close();
		entityManagerFactory.close();
	}

}
