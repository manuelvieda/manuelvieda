/**
 * UnaCloud - High Performance Computing on the Cloud
 * Manuel Eduardo Vieda Salomón (mail@manuelvieda.com)
 *
 * Creation: 24/10/2011
 * Project: UnaCloudEJB
 * Package: com.manuelvieda.unacloud.test.dao
 * File:    TestUserDao.java
 */
package com.manuelvieda.unacloud.test.dao;

import org.junit.Before;
import org.junit.Test;

import com.manuelvieda.unacloud.entities.general.Role;
import com.manuelvieda.unacloud.entities.general.User;
import com.manuelvieda.unacloud.repository.impl.JpaUserDao;

/**
 *
 * @author Manuel Eduardo Vieda Salomon  (mail@manuelvieda.com)
 * @version
 * @since
 */
public class TestUserDao {
	
	private User user;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		
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
	 * Test method for {@link com.manuelvieda.unacloud.repository.impl.JpaUserDao#createUser(com.manuelvieda.unacloud.entities.general.User)}.
	 */
	@Test
	public void testCreateUser() {
		JpaUserDao userDao= new JpaUserDao();
		userDao.createUser(user);
		
	}

}
