package com.manuelvieda.unacloud.entities.general;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the userinstances database table.
 * 
 */
@Entity
@Table(name="userinstances")
public class UserInstance implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="USERINSTANCES_ID_GENERATOR", sequenceName="SEQ_USERINSTANCES")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="USERINSTANCES_ID_GENERATOR")
	@Column(unique=true, nullable=false)
	private int id;

	@Column(length=150)
	private String dnsName;

	//bi-directional many-to-one association to InstanceParameter
	@OneToMany(mappedBy="userinstance")
	private List<InstanceParameter> instanceparameters;

	//bi-directional many-to-one association to Job
	@OneToMany(mappedBy="userinstance")
	private List<Job> jobs;

	//bi-directional many-to-one association to MonitoringLog
	@OneToMany(mappedBy="userinstance")
	private List<MonitoringLog> monitoringlogs;

	//bi-directional many-to-one association to Cluster
    @ManyToOne
	@JoinColumn(name="cluster", nullable=false)
	private Cluster cluster;

	//bi-directional many-to-one association to InstanceType
    @ManyToOne
	@JoinColumn(name="type", nullable=false)
	private InstanceType instancetype;

	//bi-directional many-to-one association to State
    @ManyToOne
	@JoinColumn(name="state", nullable=false)
	private State state;

    public UserInstance() {
    }

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDnsName() {
		return this.dnsName;
	}

	public void setDnsName(String dnsName) {
		this.dnsName = dnsName;
	}

	public List<InstanceParameter> getInstanceparameters() {
		return this.instanceparameters;
	}

	public void setInstanceparameters(List<InstanceParameter> instanceparameters) {
		this.instanceparameters = instanceparameters;
	}
	
	public List<Job> getJobs() {
		return this.jobs;
	}

	public void setJobs(List<Job> jobs) {
		this.jobs = jobs;
	}
	
	public List<MonitoringLog> getMonitoringlogs() {
		return this.monitoringlogs;
	}

	public void setMonitoringlogs(List<MonitoringLog> monitoringlogs) {
		this.monitoringlogs = monitoringlogs;
	}
	
	public Cluster getCluster() {
		return this.cluster;
	}

	public void setCluster(Cluster cluster) {
		this.cluster = cluster;
	}
	
	public InstanceType getInstancetype() {
		return this.instancetype;
	}

	public void setInstancetype(InstanceType instancetype) {
		this.instancetype = instancetype;
	}
	
	public State getState() {
		return this.state;
	}

	public void setState(State state) {
		this.state = state;
	}
	
}