package com.manuelvieda.unacloud.utils.ldap;

import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import com.unboundid.ldap.sdk.LDAPConnection;
import com.unboundid.ldap.sdk.LDAPConnectionOptions;
import com.unboundid.ldap.sdk.LDAPConnectionPool;
import com.unboundid.ldap.sdk.LDAPException;

/**
 * Session Bean implementation class LdapConnector
 */
@Singleton(name = "ldapConnectorBean")
@Startup
@LocalBean
public class LdapConnector {
	
	private final static String LDAP_HOST = "127.0.0.1";
	private final static int LDAP_PORT = 389;
	private final static String LDAP_LINK_ND = "cn=Unacloud Directory Manager";
	private final static String LDAP_PASSWORD = "adminComit";
	
	private LDAPConnectionPool pool;
	
	
    /**
     * Default constructor. 
     */
    public LdapConnector() {
    	try {
    		System.out.println("CREATING LDAP CONNECTOR");
    		LDAPConnectionOptions connectionOptions = new LDAPConnectionOptions();
			connectionOptions.setAbandonOnTimeout(true);
			connectionOptions.setAutoReconnect(true);
			connectionOptions.setBindWithDNRequiresPassword(true);
			LDAPConnection connection = new LDAPConnection(connectionOptions, LDAP_HOST, LDAP_PORT, LDAP_LINK_ND, LDAP_PASSWORD);
    		pool = new LDAPConnectionPool(connection, 1, 15);
    		
    	} catch (LDAPException e) {
    		e.printStackTrace();
    	}
    }
    
    /**
     * Obtains a connection with the LDAP Server from the pool
     * @return - An active connection with LDAP Server
     */
    public LDAPConnection getLdapConnection(){
    	
    	try {
			return pool.getConnection();
		} catch (LDAPException e) {
			e.printStackTrace();
			return null;
		}
    }
    
    /**
     * Returns a connection with the LDAP Server to the pool
     * @param connection - An active connection with LDAP Server
     */
    public void releaseLdapConnection(LDAPConnection connection){
    	pool.releaseConnection(connection);
    	
    }
    
}
