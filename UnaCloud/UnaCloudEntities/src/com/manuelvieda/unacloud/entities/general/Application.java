package com.manuelvieda.unacloud.entities.general;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the applications database table.
 * 
 */
@Entity
@Table(name="applications")
public class Application implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="APPLICATIONS_ID_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="APPLICATIONS_ID_GENERATOR")
	@Column(unique=true, nullable=false)
	private int id;

    @Lob()
	@Column(nullable=false)
	private String description;

	@Column(nullable=false, length=100)
	private String name;

	//bi-directional many-to-one association to ApplicationParameter
	@OneToMany(mappedBy="applicationBean")
	private List<ApplicationParameter> applicationparameters;

	//bi-directional many-to-one association to State
    @ManyToOne
	@JoinColumn(name="state", nullable=false)
	private State stateBean;

	//bi-directional many-to-one association to Job
	@OneToMany(mappedBy="applicationBean")
	private List<Job> jobs;

    public Application() {
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

	public List<ApplicationParameter> getApplicationparameters() {
		return this.applicationparameters;
	}

	public void setApplicationparameters(List<ApplicationParameter> applicationparameters) {
		this.applicationparameters = applicationparameters;
	}
	
	public State getStateBean() {
		return this.stateBean;
	}

	public void setStateBean(State stateBean) {
		this.stateBean = stateBean;
	}
	
	public List<Job> getJobs() {
		return this.jobs;
	}

	public void setJobs(List<Job> jobs) {
		this.jobs = jobs;
	}
	
}