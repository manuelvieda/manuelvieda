/**
 * UnaCloud - High Performance Computing on the Cloud
 * Manuel Eduardo Vieda Salomón (mail@manuelvieda.com)
 *
 * Creation: 02/11/2011
 * Project: UnaCloudEJB
 * Package: com.manuelvieda.unacloud.provider
 * File:    ICloudProvider.java
 */
package com.manuelvieda.unacloud.provider;

import javax.ejb.Local;

import com.manuelvieda.unacloud.entities.general.Cluster;

/**
 *
 * @author Manuel Eduardo Vieda Salomon  (mail@manuelvieda.com)
 * @version	1.0
 * @since	1.0
 */
@Local
public interface ICloudProvider {
	
	/**
	 * 
	 * @param cluster
	 */
	public void turnOnCluster(Cluster cluster);
	
	/**
	 * 
	 * @param cluster
	 */
	public void turnOffCluster(Cluster cluster);

}
