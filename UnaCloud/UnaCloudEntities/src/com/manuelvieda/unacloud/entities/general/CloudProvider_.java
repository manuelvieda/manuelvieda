package com.manuelvieda.unacloud.entities.general;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2011-11-15T04:16:35.335-0500")
@StaticMetamodel(CloudProvider.class)
public class CloudProvider_ {
	public static volatile SingularAttribute<CloudProvider, Integer> id;
	public static volatile SingularAttribute<CloudProvider, String> description;
	public static volatile SingularAttribute<CloudProvider, String> name;
	public static volatile SingularAttribute<CloudProvider, String> url;
	public static volatile SingularAttribute<CloudProvider, State> state;
	public static volatile ListAttribute<CloudProvider, InstanceType> instancetypes;
}
