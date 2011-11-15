package com.manuelvieda.unacloud.beans.user;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;

import com.manuelvieda.unacloud.entities.general.User;
import com.manuelvieda.unacloud.repository.dao.UserDao;
import com.manuelvieda.unacloud.repository.impl.JpaUserDao;

/**
 * Session Bean implementation class CreateUserBean
 */
@Stateless (name="unacloudCreateUserBean")
public class CreateUserBean implements CreateUserBeanLocal {
	
	private UserDao userDao;
	

	
	private String nombre;

    /**
	 * Default constructor. 
	 */
	public CreateUserBean() {
	}

	
	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}
	
	 @PostConstruct
	    public void init(){
		 userDao = new JpaUserDao();
		 nombre = "manuel Vieda";
	 }

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the userDao
	 */
	public UserDao getUserDao() {
		return userDao;
	}

	/**
	 * @param userDao the userDao to set
	 */
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	/*
     * (non-Javadoc)
     * @see com.manuelvieda.unacloud.beans.user.CreateUserBeanLocal#getCreateUser(com.manuelvieda.unacloud.entities.general.User)
     */
	@Override
	
	public void getCreateUser() {
		userDao.createUser(null);
	}
	public void getCreateUser(User user) {
		userDao.createUser(user);
	}

}
