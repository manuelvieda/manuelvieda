/**
 * UnaCloud - High Performance Computing on the Cloud
 * Manuel Eduardo Vieda Salomón (mail@manuelvieda.com)
 *
 * Creation: 23/10/2011
 * Project: UnaCloudUtils
 * Package: com.manuelvieda.unacloud.utils.ldap.test
 * File:    TestLDAPUtils.java
 */
package com.manuelvieda.unacloud.utils.ldap.test;



import javax.ejb.EJB;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.manuelvieda.unacloud.entities.general.Role;
import com.manuelvieda.unacloud.entities.general.User;
import com.manuelvieda.unacloud.utils.ldap.LdapUtils;

/**
 *
 * @author Manuel Eduardo Vieda Salomon  (mail@manuelvieda.com)
 * @version
 * @since
 */
public class TestLDAPUtils {

	@EJB
	private LdapUtils ldapUtils;
	
	private User user;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		ldapUtils = new LdapUtils();
		
		user = new User();

		user.setFirstName("Administrador");
		user.setLastName("UnaCloud");
		user.setEmail("admin@manuelvieda.com");
		user.setId("admin");
		user.setPassword("admin");
		
		Role role = new Role();
		role.setName("Users");
		user.setRole(role);

		
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
//		try {
//			LDAPConnection connection = ldapUtils.getConnection();
//			SearchResult searchResult = connection.search("dc=unacloud,dc=manuelvieda,dc=com",
//			        SearchScope.SUB, "(uid=manuelvieda)", "cn");
//			
//			System.out.println(searchResult.getEntryCount() + " entries returned.");
//			for (SearchResultEntry e : searchResult.getSearchEntries())
//			{
//			  System.out.println(e.toLDIFString());
//			  System.out.println();
//			}
//		} catch (LDAPSearchException e) {
//			e.printStackTrace();
//			fail();
//		}
	}
	
	@Test
	public void testCreateLdapUser(){
		ldapUtils.addUser(user);
		Assert.assertTrue("The user wasnt created correctly.", ldapUtils.existUser(user.getId()));
	}
	
	@Test
	public void testRemoveLdapUser(){
		
		ldapUtils.removesUser(user.getId());
		Assert.assertFalse("The user wasnt removed correctly.", ldapUtils.existUser(user.getId()));
		
	}

}
