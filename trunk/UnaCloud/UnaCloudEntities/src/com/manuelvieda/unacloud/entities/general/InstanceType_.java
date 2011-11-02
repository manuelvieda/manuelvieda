package com.manuelvieda.unacloud.entities.general;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2011-10-24T04:32:38.671-0500")
@StaticMetamodel(InstanceType.class)
public class InstanceType_ {
	public static volatile SingularAttribute<InstanceType, Integer> id;
	public static volatile SingularAttribute<InstanceType, String> description;
	public static volatile SingularAttribute<InstanceType, String> name;
	public static volatile SingularAttribute<InstanceType, Double> price;
	public static volatile SingularAttribute<InstanceType, CloudProvider> cloudprovider;
	public static volatile ListAttribute<InstanceType, UserInstance> userinstances;
}
