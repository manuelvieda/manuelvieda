/**
 * UnaCloud - High Performance Computing on the Cloud
 * Manuel Eduardo Vieda Salomón (mail@manuelvieda.com)
 *
 * Creation: 30/11/2011
 * Project: UnaCloudEJB
 * Package: com.manuelvieda.unacoud.client
 * File:    UnaCloudScketServerThread.java
 */
package com.manuelvieda.unacoud.client;

import java.io.IOException;
import java.net.ServerSocket;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;

import com.manuelvieda.unacloud.repository.services.JobService;

/**
 *
 * @author Manuel Eduardo Vieda Salomon  (mail@manuelvieda.com)
 * @version
 * @since
 */
@Singleton
public class UnaCloudScketServerThread {
	
	@EJB
	private JobService jobService;
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	@PostConstruct
	public void run(){
		ThreadConnections tc = new ThreadConnections(jobService);
		tc.start();
		
	}

	/**
	 * 
	 */
	public void check() {
		System.out.println(" --  Verificar conexiones");
		
	}

}
