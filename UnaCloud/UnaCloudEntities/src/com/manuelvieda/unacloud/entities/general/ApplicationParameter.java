package com.manuelvieda.unacloud.entities.general;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the applicationparameters database table.
 * 
 */
@Entity
@Table(name="applicationparameters")
public class ApplicationParameter implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="APPLICATIONPARAMETERS_ID_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="APPLICATIONPARAMETERS_ID_GENERATOR")
	@Column(unique=true, nullable=false)
	private int id;

	@Column(length=50)
	private String description;

	@Column(nullable=false)
	private byte mandatory;

	@Column(nullable=false, length=50)
	private String name;

	@Column(nullable=false, length=20)
	private String type;

	//bi-directional many-to-one association to Application
    @ManyToOne
	@JoinColumn(name="application", nullable=false)
	private Application applicationBean;

    public ApplicationParameter() {
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

	public byte getMandatory() {
		return this.mandatory;
	}

	public void setMandatory(byte mandatory) {
		this.mandatory = mandatory;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Application getApplicationBean() {
		return this.applicationBean;
	}

	public void setApplicationBean(Application applicationBean) {
		this.applicationBean = applicationBean;
	}
	
}