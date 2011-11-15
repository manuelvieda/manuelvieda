/**
 * UnaCloud - High Performance Computing on the Cloud
 * Manuel Eduardo Vieda Salomón (mail@manuelvieda.com)
 *
 * Creation: 07/11/2011
 * Project: UnaCloudEJB
 * Package: com.manuelvieda.unacloud.test.provider.amazon
 * File:    TestUnaCloudAmazonEC2.java
 */
package com.manuelvieda.unacloud.test.provider.amazon;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

//import com.manuelvieda.unacloud.provider.amazon.UnaCloudAmazonEC2;

/**
 *
 * @author Manuel Eduardo Vieda Salomon  (mail@manuelvieda.com)
 * @version
 * @since
 */
public class TestUnaCloudAmazonEC2 {
	
	//private UnaCloudAmazonEC2 unaCloudAmazonEC2;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		//unaCloudAmazonEC2 = new UnaCloudAmazonEC2();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link com.manuelvieda.unacloud.provider.amazon.UnaCloudAmazonEC2#requestInstance(java.lang.String, java.lang.String, java.lang.String, java.lang.String, int, boolean, java.lang.String)}.
	 */
	@Test
	public void testRequestInstance() {
		//unaCloudAmazonEC2.requestInstance("us-east-1a", "manuelvieda-testCluster", "ami-1b814f72", "t1.micro", 1, false, "pruebasUnaCloud");
		
	}

	/**
	 * Test method for {@link com.manuelvieda.unacloud.provider.amazon.UnaCloudAmazonEC2#createCluster(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testCreateCluster() {
		//unaCloudAmazonEC2.createCluster("manuelvieda", "testCluster");
	}

}
