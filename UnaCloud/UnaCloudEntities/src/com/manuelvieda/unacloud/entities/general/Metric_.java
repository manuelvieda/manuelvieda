package com.manuelvieda.unacloud.entities.general;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2011-10-24T04:32:38.676-0500")
@StaticMetamodel(Metric.class)
public class Metric_ {
	public static volatile SingularAttribute<Metric, Integer> id;
	public static volatile SingularAttribute<Metric, String> name;
	public static volatile ListAttribute<Metric, MonitoringLog> monitoringlogs;
}
