package com.manuelvieda.unacloud.entities.general;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the instancetype database table.
 * 
 */
@Entity
@Table(name="instancetype")
public class InstanceType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="INSTANCETYPE_ID_GENERATOR", sequenceName="SEQ_INSTANCETYPE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="INSTANCETYPE_ID_GENERATOR")
	@Column(unique=true, nullable=false)
	private int id;

	@Column(nullable=false, length=255)
	private String description;

	@Column(nullable=false, length=100)
	private String name;

	@Column(nullable=false)
	private double price;

	//bi-directional many-to-one association to CloudProvider
    @ManyToOne
	@JoinColumn(name="provider", nullable=false)
	private CloudProvider cloudprovider;

	//bi-directional many-to-one association to UserInstance
	@OneToMany(mappedBy="instancetype")
	private List<UserInstance> userinstances;

    public InstanceType() {
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

	public double getPrice() {
		return this.price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public CloudProvider getCloudprovider() {
		return this.cloudprovider;
	}

	public void setCloudprovider(CloudProvider cloudprovider) {
		this.cloudprovider = cloudprovider;
	}
	
	public List<UserInstance> getUserinstances() {
		return this.userinstances;
	}

	public void setUserinstances(List<UserInstance> userinstances) {
		this.userinstances = userinstances;
	}
	
}