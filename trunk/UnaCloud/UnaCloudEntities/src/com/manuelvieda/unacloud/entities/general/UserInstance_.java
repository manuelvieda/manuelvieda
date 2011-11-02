package com.manuelvieda.unacloud.entities.general;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2011-10-24T04:32:38.688-0500")
@StaticMetamodel(UserInstance.class)
public class UserInstance_ {
	public static volatile SingularAttribute<UserInstance, Integer> id;
	public static volatile SingularAttribute<UserInstance, String> dnsName;
	public static volatile ListAttribute<UserInstance, Job> jobs;
	public static volatile ListAttribute<UserInstance, MonitoringLog> monitoringlogs;
	public static volatile SingularAttribute<UserInstance, Cluster> clusterBean;
	public static volatile SingularAttribute<UserInstance, InstanceType> instancetype;
	public static volatile SingularAttribute<UserInstance, State> stateBean;
}
