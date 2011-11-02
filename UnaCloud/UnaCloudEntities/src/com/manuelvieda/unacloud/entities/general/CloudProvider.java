package com.manuelvieda.unacloud.entities.general;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the cloudproviders database table.
 * 
 */
@Entity
@Table(name="cloudproviders")
public class CloudProvider implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CLOUDPROVIDERS_ID_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CLOUDPROVIDERS_ID_GENERATOR")
	@Column(unique=true, nullable=false)
	private int id;

    @Lob()
	@Column(nullable=false)
	private String description;

	@Column(nullable=false, length=150)
	private String name;

	@Column(length=150)
	private String url;

	//bi-directional many-to-one association to State
    @ManyToOne
	@JoinColumn(name="state", nullable=false)
	private State stateBean;

	//bi-directional many-to-one association to InstanceType
	@OneToMany(mappedBy="cloudprovider")
	private List<InstanceType> instancetypes;

    public CloudProvider() {
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

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public State getStateBean() {
		return this.stateBean;
	}

	public void setStateBean(State stateBean) {
		this.stateBean = stateBean;
	}
	
	public List<InstanceType> getInstancetypes() {
		return this.instancetypes;
	}

	public void setInstancetypes(List<InstanceType> instancetypes) {
		this.instancetypes = instancetypes;
	}
	
}