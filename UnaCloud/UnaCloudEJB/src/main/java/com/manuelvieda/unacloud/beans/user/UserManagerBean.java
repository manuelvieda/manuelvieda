package com.manuelvieda.unacloud.beans.user;

import java.util.Calendar;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.manuelvieda.unacloud.entities.general.User;
import com.manuelvieda.unacloud.repository.dao.UserDao;
import com.manuelvieda.unacloud.repository.impl.JpaUserDao;

/**
 * Session Bean implementation class UserManagerBean
 */
@Stateless(mappedName = "userManagerBean")
@LocalBean
public class UserManagerBean implements UserManagerBeanLocal {
	
	private UserDao userDao;

	/**
	 * Default constructor. 
	 */
	public UserManagerBean() {
		// TODO Auto-generated constructor stub
	}

	@PostConstruct
	public void init(){
		userDao = new JpaUserDao();
	}

	public void setUser(String username, String firstname, String lastname, String password){
		User user = new User();
		user.setId("tst");
		user.setFirstName("test");
		user.setLastName("test");
		user.setPassword("test");
		user.setEmail("email");
		user.setRegistrationTime(Calendar.getInstance().getTime());

		// Stoe info on database
		userDao.createUser(user);

		// Store info on LDAP
	}



}
