package com.manuelvieda.unacloud.entities.general;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the users database table.
 * 
 */
@Entity
@Table(name="users")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false, length=50)
	private String id;

	@Column(nullable=false, length=150)
	private String email;

	@Column(nullable=false, length=50)
	private String firstName;

	@Column(nullable=false)
	private Timestamp lastLoginTimestamp;

	@Column(nullable=false, length=50)
	private String lastName;

	@Column(nullable=false, length=150)
	private String password;

    @Temporal( TemporalType.TIMESTAMP)
	private Date registrationTime;

	//bi-directional many-to-one association to Cluster
	@OneToMany(mappedBy="user")
	private List<Cluster> clusters;

	//bi-directional many-to-one association to Job
	@OneToMany(mappedBy="user")
	private List<Job> jobs;

	//bi-directional many-to-one association to Role
    @ManyToOne
	@JoinColumn(name="role", nullable=false)
	private Role role;

	//bi-directional many-to-one association to State
    @ManyToOne
	@JoinColumn(name="state", nullable=false)
	private State state;

    public User() {
    }

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public Timestamp getLastLoginTimestamp() {
		return this.lastLoginTimestamp;
	}

	public void setLastLoginTimestamp(Timestamp lastLoginTimestamp) {
		this.lastLoginTimestamp = lastLoginTimestamp;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getRegistrationTime() {
		return this.registrationTime;
	}

	public void setRegistrationTime(Date registrationTime) {
		this.registrationTime = registrationTime;
	}

	public List<Cluster> getClusters() {
		return this.clusters;
	}

	public void setClusters(List<Cluster> clusters) {
		this.clusters = clusters;
	}
	
	public List<Job> getJobs() {
		return this.jobs;
	}

	public void setJobs(List<Job> jobs) {
		this.jobs = jobs;
	}
	
	public Role getRole() {
		return this.role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
	public State getState() {
		return this.state;
	}

	public void setState(State state) {
		this.state = state;
	}
	
}