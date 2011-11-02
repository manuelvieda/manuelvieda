package com.manuelvieda.unacloud.entities.general;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2011-10-24T04:32:38.652-0500")
@StaticMetamodel(Application.class)
public class Application_ {
	public static volatile SingularAttribute<Application, Integer> id;
	public static volatile SingularAttribute<Application, String> description;
	public static volatile SingularAttribute<Application, String> name;
	public static volatile ListAttribute<Application, ApplicationParameter> applicationparameters;
	public static volatile SingularAttribute<Application, State> stateBean;
	public static volatile ListAttribute<Application, Job> jobs;
}
