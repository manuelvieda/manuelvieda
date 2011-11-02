/**
 * UnaCloud - High Performance Computing on the Cloud
 * Manuel Eduardo Vieda Salomón (mail@manuelvieda.com)
 *
 * Creation: 02/11/2011
 * Project: UnaCloudEJB
 * Package: com.manuelvieda.unacloud.provider.amazon
 * File:    UnaCloudAmazonEC2.java
 */
package com.manuelvieda.unacloud.provider.amazon;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.manuelvieda.unacloud.provider.ICloudProvider;
import com.amazonaws.auth.*;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.LaunchSpecification;
import com.amazonaws.services.ec2.model.RequestSpotInstancesRequest;
import com.amazonaws.services.ec2.model.RequestSpotInstancesResult;

/**
 * Class that implements the functionality of Amazon Elastic Cloud Computing 
 * @author Manuel Eduardo Vieda Salomon  (mail@manuelvieda.com)
 * @version	1.0
 * @since	1.0
 */
public class UnaCloudAmazonEC2 implements ICloudProvider {
	
	/**
	 * Amazon AWS Autentication credentials
	 */
	private AWSCredentials credentials;
	
	/**
	 * Reference to Amazon EC2 Service
	 */
	private static  AmazonEC2 ec2;
	
	
	/**
	 * Default constructor
	 */
	public UnaCloudAmazonEC2() {
		
		retrieveCredentials();
		 ec2 = new AmazonEC2Client(credentials);
	}
	
	public void requestInstance(){
		RequestSpotInstancesRequest requestRequest = new RequestSpotInstancesRequest();
		
		LaunchSpecification launchSpecification = new LaunchSpecification();
		launchSpecification.setImageId("ami-1b814f72");
		launchSpecification.setInstanceType("t1.micro");
		 
		// Add the security group to the request.
		List<String> securityGroups = new ArrayList<String>();
		securityGroups.add("default");
		launchSpecification.setSecurityGroups(securityGroups);
		
		// Add the launch specifications to the request.
		requestRequest.setLaunchSpecification(launchSpecification);
		
		// Call the RequestSpotInstance API.
		RequestSpotInstancesResult requestResult = ec2.requestSpotInstances(requestRequest);
	}
	
	
	
	
	private void retrieveCredentials(){
		try {
			AWSCredentials credentials = new PropertiesCredentials(
					AmazonEC2.class.getResourceAsStream("./src/main/resources/AwsCredentials.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

}
