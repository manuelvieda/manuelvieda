package com.manuelvieda.unacloud.beans.user;
import javax.ejb.Local;


@Local
public interface UserManagerBeanLocal {
	public void setUser(String username, String firstname, String lastname, String password);

}
