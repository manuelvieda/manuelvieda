package com.manuelvieda.unacloud.entities.general;

import java.math.BigInteger;
import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2011-11-15T04:16:35.335-0500")
@StaticMetamodel(Job.class)
public class Job_ {
	public static volatile SingularAttribute<Job, Integer> id;
	public static volatile SingularAttribute<Job, Timestamp> creationTime;
	public static volatile SingularAttribute<Job, BigInteger> finishTime;
	public static volatile SingularAttribute<Job, String> parameters;
	public static volatile SingularAttribute<Job, String> params;
	public static volatile SingularAttribute<Job, Timestamp> procesedTime;
	public static volatile SingularAttribute<Job, Integer> state;
	public static volatile SingularAttribute<Job, Application> application;
	public static volatile SingularAttribute<Job, Cluster> cluster;
	public static volatile SingularAttribute<Job, UserInstance> userinstance;
	public static volatile SingularAttribute<Job, User> user;
}