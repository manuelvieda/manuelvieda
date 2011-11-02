package com.manuelvieda.unacloud.entities.general;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the monitoringlog database table.
 * 
 */
@Entity
@Table(name="monitoringlog")
public class MonitoringLog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="MONITORINGLOG_ID_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="MONITORINGLOG_ID_GENERATOR")
	@Column(unique=true, nullable=false)
	private String id;

	@Column(nullable=false, length=50)
	private String value;

	//bi-directional many-to-one association to Metric
    @ManyToOne
	@JoinColumn(name="metic", nullable=false)
	private Metric metric;

	//bi-directional many-to-one association to UserInstance
    @ManyToOne
	@JoinColumn(name="instance", nullable=false)
	private UserInstance userinstance;

    public MonitoringLog() {
    }

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Metric getMetric() {
		return this.metric;
	}

	public void setMetric(Metric metric) {
		this.metric = metric;
	}
	
	public UserInstance getUserinstance() {
		return this.userinstance;
	}

	public void setUserinstance(UserInstance userinstance) {
		this.userinstance = userinstance;
	}
	
}