package com.manuelvieda.unacloud.entities.general;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2011-11-27T20:50:58.076-0500")
@StaticMetamodel(ApplicationParameter.class)
public class ApplicationParameter_ {
	public static volatile SingularAttribute<ApplicationParameter, Integer> id;
	public static volatile SingularAttribute<ApplicationParameter, String> description;
	public static volatile SingularAttribute<ApplicationParameter, Boolean> mandatory;
	public static volatile SingularAttribute<ApplicationParameter, String> name;
	public static volatile SingularAttribute<ApplicationParameter, String> type;
	public static volatile SingularAttribute<ApplicationParameter, Application> application;
}
