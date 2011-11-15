package com.manuelvieda.unacloud.entities.general;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the metrics database table.
 * 
 */
@Entity
@Table(name="metrics")
public class Metric implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="METRICS_ID_GENERATOR", sequenceName="SEQ_METRICS")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="METRICS_ID_GENERATOR")
	@Column(unique=true, nullable=false)
	private int id;

	@Column(nullable=false, length=50)
	private String name;

	//bi-directional many-to-one association to MonitoringLog
	@OneToMany(mappedBy="metric")
	private List<MonitoringLog> monitoringlogs;

    public Metric() {
    }

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<MonitoringLog> getMonitoringlogs() {
		return this.monitoringlogs;
	}

	public void setMonitoringlogs(List<MonitoringLog> monitoringlogs) {
		this.monitoringlogs = monitoringlogs;
	}
	
}