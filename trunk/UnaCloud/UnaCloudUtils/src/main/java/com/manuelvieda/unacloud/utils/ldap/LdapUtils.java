/**
 * UnaCloud - High Performance Computing on the Cloud
 * Manuel Eduardo Vieda Salomón (mail@manuelvieda.com)
 *
 * Creation: 23/10/2011
 * Project: UnaCloudUtils
 * Package: com.manuelvieda.unacloud.utils.ldap
 * File:    LdapUtils.java
 */
package com.manuelvieda.unacloud.utils.ldap;

import java.util.ArrayList;

import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import com.manuelvieda.unacloud.entities.general.User;
import com.unboundid.ldap.sdk.Attribute;
import com.unboundid.ldap.sdk.LDAPConnection;
import com.unboundid.ldap.sdk.LDAPException;
import com.unboundid.ldap.sdk.LDAPResult;
import com.unboundid.ldap.sdk.LDAPSearchException;
import com.unboundid.ldap.sdk.Modification;
import com.unboundid.ldap.sdk.ModificationType;
import com.unboundid.ldap.sdk.ResultCode;
import com.unboundid.ldap.sdk.SearchResult;
import com.unboundid.ldap.sdk.SearchResultEntry;
import com.unboundid.ldap.sdk.SearchScope;

/**
 * Session Bean implementation class LdapUtils - Common functionalities for LDAP repository
 * @author Manuel Eduardo Vieda Salomon  (mail@manuelvieda.com)
 * @version	1.0
 * @since	1.0
 */
@Stateless(mappedName = "ldapUtilsBean")
@TransactionManagement(TransactionManagementType.BEAN)
@LocalBean
public class LdapUtils implements LdapUtilsLocal {

    
	private final static String LDAP_BASE_DN = "dc=unacloud,dc=manuelvieda,dc=com";
	
	/**
	 * Bean that controls the connections with LDAP Server
	 */
	@EJB
	private LdapConnector ldapConnector;
	
	/**
	 * This method establish a connection with the LDAP Server
	 */
	public LDAPConnection connect(){
		return ldapConnector.getLdapConnection();
	}
	
	/**
	 * This methid close an active connection with the LDAP Server
	 */
	@PreDestroy
	public void disconnect(LDAPConnection connection){
		if(connection!=null){
			ldapConnector.releaseLdapConnection(connection);
		}
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see com.manuelvieda.unacloud.utils.ldap.LdapUtilsLocal#addUser(com.manuelvieda.unacloud.entities.general.User)
	 */
	@Override
	public boolean addUser(User user){
		
		boolean answ = false;
		
		LDAPConnection connection = connect();
		try {
			
			ArrayList<Attribute> userAttributes = new ArrayList<Attribute>();
			userAttributes.add(new Attribute("objectClass", "person"));
			userAttributes.add(new Attribute("objectClass", "inetOrgPerson"));
			userAttributes.add(new Attribute("objectClass", "organizationalPerson"));
			userAttributes.add(new Attribute("objectClass", "top"));
			userAttributes.add(new Attribute("uid", user.getId()));
			userAttributes.add(new Attribute("mail", user.getEmail()));
			userAttributes.add(new Attribute("givenName", user.getFirstName()+" "+user.getLastName()));
			userAttributes.add(new Attribute("cn", user.getFirstName()));
			userAttributes.add(new Attribute("sn", user.getLastName()));
			userAttributes.add(new Attribute("userPassword", user.getPassword()));
			
			String DN = "uid="+user.getId()+",ou=People,"+LDAP_BASE_DN;
			
			LDAPResult addResult = connection.add(DN, userAttributes.toArray(new Attribute[userAttributes.size()]));
			
			if(addResult.getResultCode().equals(ResultCode.SUCCESS)){
				// User added to LDAP Repository.

				String groupDN = "cn="+user.getRole().getName()+",ou=groups,"+LDAP_BASE_DN;
				Modification modification = new Modification(ModificationType.ADD, "uniqueMember", DN);
				LDAPResult resultAddGroup = connection.modify(groupDN, modification);
				
				if(resultAddGroup.getResultCode().equals(ResultCode.SUCCESS))
					answ = true;
				else{
					
					// On failure - Removes the user that was alredy created
				}
			}
			
		} catch (LDAPException e) {
			e.printStackTrace();
			return false;
			
		}finally{
			disconnect(connection);
		}
		
		return answ;
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.manuelvieda.unacloud.utils.ldap.LdapUtilsLocal#assignUserToGroup(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean assignUserToGroup(String uid, String groupName){
		
		boolean answ = false;
		LDAPConnection connection = connect();
		try {
			
			if(existUser(uid)){
				String DN = "uid="+uid+",ou=People,"+LDAP_BASE_DN;
				String groupDN = "cn="+groupName+",ou=groups,"+LDAP_BASE_DN;
				
				Modification modification = new Modification(ModificationType.ADD, "uniqueMember", DN);
				LDAPResult result = connection.modify(groupDN, modification);
				
				if(result.getResultCode().equals(ResultCode.SUCCESS))
					answ = true;
			}
		} catch (LDAPException e) {
			e.printStackTrace();
			return false;
			
		}finally{
			disconnect(connection);
		}
		
		return answ;
		
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see com.manuelvieda.unacloud.utils.ldap.LdapUtilsLocal#searchUser(java.lang.String)
	 */
	@Override
	public User searchUser(String uid){
		
		User user = null;
		LDAPConnection connection = connect();
		try {
			SearchResult searchResult = connection.search(LDAP_BASE_DN,  SearchScope.SUB, "(uid=manuelvieda)", "cn");
			if(searchResult.getEntryCount()>=1){
				user = new User();
				SearchResultEntry entryLdap = searchResult.getSearchEntries().get(0);
				user.setId(entryLdap.getAttributeValue("uid"));
				user.setFirstName(entryLdap.getAttributeValue("cn"));
				user.setLastName(entryLdap.getAttributeValue("sn"));
				user.setEmail(entryLdap.getAttributeValue("mail"));
			}
				
		} catch (LDAPSearchException e) {
			e.printStackTrace();
		} finally{
			disconnect(connection);
		}
		return user;
	}
	
	public boolean existUser(String uid){
		
		boolean answ = false;
		
		LDAPConnection connection = connect();
		try {
			SearchResult searchResult = connection.search(LDAP_BASE_DN,  SearchScope.SUB, "(uid="+uid+")", "cn");
			if(searchResult.getEntryCount()>=1)
				answ = true;
				
		} catch (LDAPSearchException e) {
			e.printStackTrace();
		}finally{
			disconnect(connection);
		}
		return  answ;
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.manuelvieda.unacloud.utils.ldap.LdapUtilsLocal#removesUser(java.lang.String)
	 */
	@Override
	public boolean removesUser(String uid){
		boolean answ = false;
		LDAPConnection connection = connect();
		try {
			if(existUser(uid)){
				
				String DN = "ou=People,"+LDAP_BASE_DN;
				
				Modification modification = new Modification(ModificationType.DELETE, "uid="+uid, DN);
				LDAPResult result = connection.modify(DN, modification);
				
				if(result.getResultCode().equals(ResultCode.SUCCESS))
					answ = true;
			}
		} catch (LDAPException e) {
			e.printStackTrace();
			return false;
		}finally{
			disconnect(connection);
		}
		
		return answ;
		
	}

}
