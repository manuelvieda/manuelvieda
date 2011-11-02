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
	@SequenceGenerator(name="USERINSTANCES_ID_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="USERINSTANCES_ID_GENERATOR")
	@Column(unique=true, nullable=false)
	private int id;

	@Column(length=150)
	private String dnsName;

	//bi-directional many-to-one association to Job
	@OneToMany(mappedBy="userinstance")
	private List<Job> jobs;

	//bi-directional many-to-one association to MonitoringLog
	@OneToMany(mappedBy="userinstance")
	private List<MonitoringLog> monitoringlogs;

	//bi-directional many-to-one association to Cluster
    @ManyToOne
	@JoinColumn(name="cluster", nullable=false)
	private Cluster clusterBean;

	//bi-directional many-to-one association to InstanceType
    @ManyToOne
	@JoinColumn(name="type", nullable=false)
	private InstanceType instancetype;

	//bi-directional many-to-one association to State
    @ManyToOne
	@JoinColumn(name="state", nullable=false)
	private State stateBean;

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
	
	public Cluster getClusterBean() {
		return this.clusterBean;
	}

	public void setClusterBean(Cluster clusterBean) {
		this.clusterBean = clusterBean;
	}
	
	public InstanceType getInstancetype() {
		return this.instancetype;
	}

	public void setInstancetype(InstanceType instancetype) {
		this.instancetype = instancetype;
	}
	
	public State getStateBean() {
		return this.stateBean;
	}

	public void setStateBean(State stateBean) {
		this.stateBean = stateBean;
	}
	
}