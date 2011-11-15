package com.manuelvieda.unacloud.entities.general;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigInteger;
import java.sql.Timestamp;


/**
 * The persistent class for the jobs database table.
 * 
 */
@Entity
@Table(name="jobs")
public class Job implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="JOBS_ID_GENERATOR", sequenceName="SEQ_JOBS")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="JOBS_ID_GENERATOR")
	@Column(unique=true, nullable=false)
	private int id;

	@Column(nullable=false)
	private Timestamp creationTime;

	private BigInteger finishTime;

	@Column(nullable=false, length=255)
	private String parameters;

	@Column(length=150)
	private String params;

	@Column(nullable=false)
	private Timestamp procesedTime;

	private int state;

	//bi-directional many-to-one association to Application
    @ManyToOne
	@JoinColumn(name="application", nullable=false)
	private Application application;

	//bi-directional many-to-one association to Cluster
    @ManyToOne
	@JoinColumn(name="cluster", nullable=false)
	private Cluster cluster;

	//bi-directional many-to-one association to UserInstance
    @ManyToOne
	@JoinColumn(name="instance")
	private UserInstance userinstance;

	//bi-directional many-to-one association to User
    @ManyToOne
	@JoinColumn(name="owner", nullable=false)
	private User user;

    public Job() {
    }

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Timestamp getCreationTime() {
		return this.creationTime;
	}

	public void setCreationTime(Timestamp creationTime) {
		this.creationTime = creationTime;
	}

	public BigInteger getFinishTime() {
		return this.finishTime;
	}

	public void setFinishTime(BigInteger finishTime) {
		this.finishTime = finishTime;
	}

	public String getParameters() {
		return this.parameters;
	}

	public void setParameters(String parameters) {
		this.parameters = parameters;
	}

	public String getParams() {
		return this.params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public Timestamp getProcesedTime() {
		return this.procesedTime;
	}

	public void setProcesedTime(Timestamp procesedTime) {
		this.procesedTime = procesedTime;
	}

	public int getState() {
		return this.state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public Application getApplication() {
		return this.application;
	}

	public void setApplication(Application application) {
		this.application = application;
	}
	
	public Cluster getCluster() {
		return this.cluster;
	}

	public void setCluster(Cluster cluster) {
		this.cluster = cluster;
	}
	
	public UserInstance getUserinstance() {
		return this.userinstance;
	}

	public void setUserinstance(UserInstance userinstance) {
		this.userinstance = userinstance;
	}
	
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}