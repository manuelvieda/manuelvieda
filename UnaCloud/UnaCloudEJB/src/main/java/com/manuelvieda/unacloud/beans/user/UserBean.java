/**
 * UnaCloud - High Performance Computing on the Cloud
 * Manuel Eduardo Vieda Salomón (mail@manuelvieda.com)
 *
 * Creation: 23/10/2011
 * Project: UnaCloudEJB
 * Package: com.manuelvieda.unacloud.beans.user
 * File:    UserBean.java
 */
package com.manuelvieda.unacloud.beans.user;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import com.manuelvieda.unacloud.entities.general.User;
import com.manuelvieda.unacloud.repository.services.UserService;

/**
 * EJB that contains and controlls the user information
 * @author Manuel Eduardo Vieda
 * @version	1.0
 * @since	1.0
 */
@Stateful (name = "loggedUserInfoBean")
@TransactionManagement(TransactionManagementType.BEAN)
public class UserBean implements UserBeanLocal {
	
	@EJB
	private UserService userService;
	
	/**
	 * The given name of the current logged user (Firstname + Lastname)
	 */
	private String name;
	
	/**
	 * The username or UID of the current logged user
	 */
	private String username;
	
	// ------------------------------------------------------------------------------------
	
    /**
     * Default constructor. 
     */
    public UserBean() {
    }
    
    /**
     * Initializes the bean
     */
    @PostConstruct
    public void init(){
    	
    	ExternalContext ectx = FacesContext.getCurrentInstance().getExternalContext();
    	if(ectx.getUserPrincipal()!=null){
    		username =  ectx.getUserPrincipal().getName();
    		User user = userService.findUser(username);
    		name = user.getFirstName()+" "+user.getLastName();
    	}
    }
    
    /*
     * (non-Javadoc)
     * @see com.manuelvieda.unacloud.beans.user.UserBeanLocal#removeSession()
     */
    @Remove
    public void removeSession() throws IOException{
    	
    	ExternalContext ectx = FacesContext.getCurrentInstance().getExternalContext();
		ectx.invalidateSession();
		ectx.redirect("/"+ectx.getContextName()+"/index.html");
    	
    }
    
    // ------------------------------------------------------------------------------------


    /**
	 * @return the name
	 */
	public String getName() {
		return name;
	}


	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}


	/*
	 * (non-Javadoc)
	 * @see com.manuelvieda.unacloud.beans.user.UserBeanLocal#getUsername()
	 */
	@Override
	public String getUsername() {
		return username;
	}


	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	
	/*
	 * (non-Javadoc)
	 * @see com.manuelvieda.unacloud.beans.user.UserBeanLocal#getUserGrettings()
	 */
	@Override
	public String getUserGrettings(){
		return "Welcome " + getName(); 
	}

}
