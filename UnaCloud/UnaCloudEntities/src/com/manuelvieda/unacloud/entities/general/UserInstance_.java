package com.manuelvieda.unacloud.entities.general;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2011-11-27T14:30:00.019-0500")
@StaticMetamodel(UserInstance.class)
public class UserInstance_ {
	public static volatile SingularAttribute<UserInstance, Integer> id;
	public static volatile SingularAttribute<UserInstance, String> dnsName;
	public static volatile SingularAttribute<UserInstance, String> identifier;
	public static volatile ListAttribute<UserInstance, InstanceParameter> instanceparameters;
	public static volatile ListAttribute<UserInstance, Job> jobs;
	public static volatile ListAttribute<UserInstance, MonitoringLog> monitoringlogs;
	public static volatile SingularAttribute<UserInstance, Cluster> cluster;
	public static volatile SingularAttribute<UserInstance, InstanceType> instancetype;
	public static volatile SingularAttribute<UserInstance, State> state;
}
