/**
 * UnaCloud - High Performance Computing on the Cloud
 * Manuel Eduardo Vieda Salomón (mail@manuelvieda.com)
 *
 * Creation: 26/10/2011
 * Project: UnaCloudEntities
 * Package: com.manuelvieda.unacloud.repository.constants
 * File:    Constants.java
 */
package com.manuelvieda.unacloud.repository.constants;

/**
 * Constants used with the persistence service
 * @author Manuel Eduardo Vieda Salomon  (mail@manuelvieda.com)
 * @version	1.0
 * @since	1.0
 */
public class Constants {
	
	/**
	 * NAMED QUERIES
	 */
	
	// APPLICATIONS
	public static final String NQ_APPLICATION_FINAL_ALL = "application.findAll";
	
	// APPLICATION PARAMETERS
	public static final String NQ_APPLICATION_PARAMETERS_GET_BY_APP = "applicationParameter.byApplication";
	
	// CLOUD PROVIDER
	public static final String NQ_CLOUD_PROVIDER_FIND_ALL = "cloudProvider.findAll";
	
	// JOB
	public static final String NQ_JOB_FIND_BY_USER = "job.findByUser";
	public static final String NQ_JOB_UPDATE_RESULT = "job.updateResult";
	// INSTANCE TYPE
	public static final String NQ_INSTANCE_TYPE_GETALLL_BY_PROVIDER = "instanceType.findAllByProvider";

	// CLUSTER
	public static final String NQ_CLUSTER_FIND_BY_USER = "cluster.findByUser";

	// USER INSTANCES
	public static final String NQ_USER_INSTANCES_FIND_BY_USER = "userInstances.findByUser";
	public static final String NQ_USER_INSTANCES_FIND_BY_USER_CLUSTER = "userInstances.findByUserCluster";
	
	
	// STATUS
	public static final int STATE_CLUSTER_ID_STANDBY = 11;
	public static final int STATE_USER_INSTANCE_ID_STANDBY = 15;
	public static final int STATE_USER_INSTANCE_ID_RUNNING = 16;
	public static final int STATE_JOB_CREATED = 19;


	 

}
