package com.manuelvieda.unacloud.entities.general;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2011-10-24T04:32:38.678-0500")
@StaticMetamodel(MonitoringLog.class)
public class MonitoringLog_ {
	public static volatile SingularAttribute<MonitoringLog, String> id;
	public static volatile SingularAttribute<MonitoringLog, String> value;
	public static volatile SingularAttribute<MonitoringLog, Metric> metric;
	public static volatile SingularAttribute<MonitoringLog, UserInstance> userinstance;
}
