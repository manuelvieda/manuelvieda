/**
 * UnaCloud - High Performance Computing on the Cloud
 * Manuel Eduardo Vieda Salomón (mail@manuelvieda.com)
 *
 * Creation: 26/10/2011
 * Project: UnaCloudWebPortal
 * Package: com.manuelvieda.unacloud.entities
 * File:    Instance.java
 */
package com.manuelvieda.unacloud.entities;

/**
 * Represent a Cluster Instante
 * @author Manuel Eduardo Vieda Salomon  (mail@manuelvieda.com)
 * @version	1.0
 * @since	1.0
 */
public class Instance {
	
	/**
	 * Internadl identification of the instance
	 */
	private int id;
	
	/**
	 * Instance type Name and ID 
	 */
	private String instanceType;
	private int idInstanceType;
	
	/**
	 * Provider Name and ID
	 */
	private String provider;
	private int idProvider;
	
	
	/**
	 * Quiantity of this instance type on the cluster
	 */
	private int quantity;
	
	/**
	 * Price (per hour) of the instance
	 */
	private double price;
	
	
	// ---------------------------------------------------------
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the instanceType
	 */
	public String getInstanceType() {
		return instanceType;
	}

	/**
	 * @param instanceType the instanceType to set
	 */
	public void setInstanceType(String instanceType) {
		this.instanceType = instanceType;
	}

	/**
	 * @return the idInstanceType
	 */
	public int getIdInstanceType() {
		return idInstanceType;
	}

	/**
	 * @param idInstanceType the idInstanceType to set
	 */
	public void setIdInstanceType(int idInstanceType) {
		this.idInstanceType = idInstanceType;
	}

	/**
	 * @return the provider
	 */
	public String getProvider() {
		return provider;
	}

	/**
	 * @param provider the provider to set
	 */
	public void setProvider(String provider) {
		this.provider = provider;
	}

	/**
	 * @return the idProvider
	 */
	public int getIdProvider() {
		return idProvider;
	}

	/**
	 * @param idProvider the idProvider to set
	 */
	public void setIdProvider(int idProvider) {
		this.idProvider = idProvider;
	}

	/**
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(double price) {
		this.price = price;
	}
	

}
