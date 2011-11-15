package com.manuelvieda.unacloud.entities.general;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the states database table.
 * 
 */
@Entity
@Table(name="states")
public class State implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="STATES_ID_GENERATOR", sequenceName="SEQ_STATES")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="STATES_ID_GENERATOR")
	@Column(unique=true, nullable=false)
	private int id;

	@Column(nullable=false, length=250)
	private String description;

	@Column(nullable=false, length=3)
	private String domain;

	//bi-directional many-to-one association to Application
	@OneToMany(mappedBy="state")
	private List<Application> applications;

	//bi-directional many-to-one association to CloudProvider
	@OneToMany(mappedBy="state")
	private List<CloudProvider> cloudproviders;

	//bi-directional many-to-one association to Cluster
	@OneToMany(mappedBy="state")
	private List<Cluster> clusters;

	//bi-directional many-to-one association to UserInstance
	@OneToMany(mappedBy="state")
	private List<UserInstance> userinstances;

	//bi-directional many-to-one association to User
	@OneToMany(mappedBy="state")
	private List<User> users;

    public State() {
    }

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDomain() {
		return this.domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public List<Application> getApplications() {
		return this.applications;
	}

	public void setApplications(List<Application> applications) {
		this.applications = applications;
	}
	
	public List<CloudProvider> getCloudproviders() {
		return this.cloudproviders;
	}

	public void setCloudproviders(List<CloudProvider> cloudproviders) {
		this.cloudproviders = cloudproviders;
	}
	
	public List<Cluster> getClusters() {
		return this.clusters;
	}

	public void setClusters(List<Cluster> clusters) {
		this.clusters = clusters;
	}
	
	public List<UserInstance> getUserinstances() {
		return this.userinstances;
	}

	public void setUserinstances(List<UserInstance> userinstances) {
		this.userinstances = userinstances;
	}
	
	public List<User> getUsers() {
		return this.users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
	
}