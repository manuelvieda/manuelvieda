package com.manuelvieda.unacloud.entities.general;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2011-11-15T04:16:35.335-0500")
@StaticMetamodel(Cluster.class)
public class Cluster_ {
	public static volatile SingularAttribute<Cluster, Integer> id;
	public static volatile SingularAttribute<Cluster, String> description;
	public static volatile SingularAttribute<Cluster, String> name;
	public static volatile SingularAttribute<Cluster, State> state;
	public static volatile SingularAttribute<Cluster, User> user;
	public static volatile ListAttribute<Cluster, Job> jobs;
	public static volatile ListAttribute<Cluster, UserInstance> userinstances;
}
