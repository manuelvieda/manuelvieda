package com.manuelvieda.unacloud.entities.general;

import java.sql.Timestamp;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2011-10-24T04:32:38.686-0500")
@StaticMetamodel(User.class)
public class User_ {
	public static volatile SingularAttribute<User, String> id;
	public static volatile SingularAttribute<User, String> email;
	public static volatile SingularAttribute<User, String> firstName;
	public static volatile SingularAttribute<User, Timestamp> lastLoginTimestamp;
	public static volatile SingularAttribute<User, String> lastName;
	public static volatile SingularAttribute<User, String> password;
	public static volatile SingularAttribute<User, Date> registrationTime;
	public static volatile ListAttribute<User, Cluster> clusters;
	public static volatile ListAttribute<User, Job> jobs;
	public static volatile SingularAttribute<User, State> stateBean;
	public static volatile SingularAttribute<User, Role> role;
}
