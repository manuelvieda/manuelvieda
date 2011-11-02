/**
 * UnaCloud - High Performance Computing on the Cloud
 * Manuel Eduardo Vieda Salomón (mail@manuelvieda.com)
 *
 * Creation: 28/10/2011
 * Project: UnaCloudWebPortal
 * Package: com.manuelvieda.unacloud.entities
 * File:    Cluster.java
 */
package com.manuelvieda.unacloud.entities;

/**
 *
 * @author Manuel Eduardo Vieda Salomon  (mail@manuelvieda.com)
 * @version	1.0
 * @since	1.0
 */
public class Cluster {
	
	private String name;
	private String description;
	
	private String state;
	private int idState;
	
	private int totalInstances;
	
	//----------------------------------------------------------


	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the idState
	 */
	public int getIdState() {
		return idState;
	}

	/**
	 * @param idState the idState to set
	 */
	public void setIdState(int idState) {
		this.idState = idState;
	}

	/**
	 * @return the totalInstances
	 */
	public int getTotalInstances() {
		return totalInstances;
	}

	/**
	 * @param totalInstances the totalInstances to set
	 */
	public void setTotalInstances(int totalInstances) {
		this.totalInstances = totalInstances;
	}
}
