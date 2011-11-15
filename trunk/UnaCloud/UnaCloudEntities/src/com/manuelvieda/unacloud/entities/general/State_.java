package com.manuelvieda.unacloud.entities.general;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2011-11-15T04:16:35.345-0500")
@StaticMetamodel(State.class)
public class State_ {
	public static volatile SingularAttribute<State, Integer> id;
	public static volatile SingularAttribute<State, String> description;
	public static volatile SingularAttribute<State, String> domain;
	public static volatile ListAttribute<State, Application> applications;
	public static volatile ListAttribute<State, CloudProvider> cloudproviders;
	public static volatile ListAttribute<State, Cluster> clusters;
	public static volatile ListAttribute<State, UserInstance> userinstances;
	public static volatile ListAttribute<State, User> users;
}
