package com.manuelvieda.unacloud.entities.general;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the clusters database table.
 * 
 */
@Entity
@Table(name="clusters")
public class Cluster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CLUSTERS_ID_GENERATOR", sequenceName="SEQ_CLUSTERS")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CLUSTERS_ID_GENERATOR")
	@Column(unique=true, nullable=false)
	private int id;

	@Column(length=250)
	private String description;

	@Column(nullable=false, length=50)
	private String name;

	//bi-directional many-to-one association to State
    @ManyToOne
	@JoinColumn(name="state", nullable=false)
	private State state;

	//bi-directional many-to-one association to User
    @ManyToOne
	@JoinColumn(name="owner", nullable=false)
	private User user;

	//bi-directional many-to-one association to Job
	@OneToMany(mappedBy="cluster")
	private List<Job> jobs;

	//bi-directional many-to-one association to UserInstance
	@OneToMany(mappedBy="cluster")
	private List<UserInstance> userinstances;

    public Cluster() {
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

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public State getState() {
		return this.state;
	}

	public void setState(State state) {
		this.state = state;
	}
	
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public List<Job> getJobs() {
		return this.jobs;
	}

	public void setJobs(List<Job> jobs) {
		this.jobs = jobs;
	}
	
	public List<UserInstance> getUserinstances() {
		return this.userinstances;
	}

	public void setUserinstances(List<UserInstance> userinstances) {
		this.userinstances = userinstances;
	}
	
}