package com.manuelvieda.unacloud.entities.general;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the instanceparameters database table.
 * 
 */
@Entity
@Table(name="instanceparameters")
public class InstanceParameter implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="INSTANCEPARAMETERS_ID_GENERATOR", sequenceName="SEQINSTANCEPARAMETER")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="INSTANCEPARAMETERS_ID_GENERATOR")
	@Column(unique=true, nullable=false)
	private int id;

	@Column(nullable=false)
	private int tag;

	@Column(nullable=false, length=50)
	private String value;

	//bi-directional many-to-one association to UserInstance
    @ManyToOne
	@JoinColumn(name="instance", nullable=false)
	private UserInstance userinstance;

    public InstanceParameter() {
    }

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTag() {
		return this.tag;
	}

	public void setTag(int tag) {
		this.tag = tag;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public UserInstance getUserinstance() {
		return this.userinstance;
	}

	public void setUserinstance(UserInstance userinstance) {
		this.userinstance = userinstance;
	}
	
}