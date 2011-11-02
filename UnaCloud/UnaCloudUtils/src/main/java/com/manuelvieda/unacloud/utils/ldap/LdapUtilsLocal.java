/**
 * UnaCloud - High Performance Computing on the Cloud
 * Manuel Eduardo Vieda Salomón (mail@manuelvieda.com)
 *
 * Creation: 23/10/2011
 * Project: UnaCloudUtils
 * Package: com.manuelvieda.unacloud.utils.ldap
 * File:    LdapUtilsLocal.java
 */
package com.manuelvieda.unacloud.utils.ldap;
import javax.ejb.Local;

import com.manuelvieda.unacloud.entities.general.User;


/**
 * Local Interface for Session Bean implementation class LdapUtils - Common functionalities for LDAP repository
 * @author Manuel Eduardo Vieda Salomon  (mail@manuelvieda.com)
 * @version	1.0
 * @since	1.0
 */
@Local
public interface LdapUtilsLocal {
	
	/**
	 * This methods create an user on the LDAP repository
	 * @param user - The entity of the new user
	 */
	public boolean addUser(User user);
	
	/**
	 * Removes an user from the LDAP Repository
	 * @param uid - The user id/uid
	 * @return True if the user was succesfullly removed, False otherwise
	 */
	public boolean removesUser(String uid);
	
	/**
	 * Add an existent user to a the specific group
	 * @param uid - The user id/uid
	 * @param groupName - The name of the group
	 * @return True if the user was succesfully asigned to the group, False otherwise, 
	 */
	public boolean assignUserToGroup(String uid, String groupName);
	
	/**
	 * Search an user by its uid
	 * @param uid - The user id/uid
	 * @return User - The User identified by the given uid, Null if that user dont exist in the LDAP repository.
	 */
	public User searchUser(String uid);

}
