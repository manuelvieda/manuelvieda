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

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ejb.Stateless;

import org.apache.commons.collections.CollectionUtils;

import com.manuelvieda.unacloud.entities.general.Cluster;
import com.manuelvieda.unacloud.entities.general.UserInstance;
import com.manuelvieda.unacloud.provider.ICloudProvider;
import com.manuelvieda.unacloud.repository.constants.AWSConstants;
import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.*;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.AvailabilityZone;
import com.amazonaws.services.ec2.model.CreatePlacementGroupRequest;
import com.amazonaws.services.ec2.model.DescribeAvailabilityZonesResult;
import com.amazonaws.services.ec2.model.DescribeSecurityGroupsResult;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.LaunchSpecification;
import com.amazonaws.services.ec2.model.Placement;
import com.amazonaws.services.ec2.model.RequestSpotInstancesRequest;
import com.amazonaws.services.ec2.model.RequestSpotInstancesResult;
import com.amazonaws.services.ec2.model.Reservation;
import com.amazonaws.services.ec2.model.RunInstancesRequest;
import com.amazonaws.services.ec2.model.RunInstancesResult;
import com.amazonaws.services.ec2.model.SecurityGroup;

/**
 * Class that implements the functionality of Amazon Elastic Cloud Computing 
 * @author Manuel Eduardo Vieda Salomon  (mail@manuelvieda.com)
 * @version	1.0
 * @since	1.0
 */
@Stateless (name="unaCloudAmazonEC2Bean")
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
	
	
	// -------------------------------------------------------------
	// CLUSTER PROVIDER INTERFACE
	// -------------------------------------------------------------
	
	/* (non-Javadoc)
	 * @see com.manuelvieda.unacloud.provider.ICloudProvider#turnOnCluster(com.manuelvieda.unacloud.entities.general.Cluster)
	 */
	@Override
	public void turnOnCluster(Cluster cluster) {
		
		String availabilityZone = "us-east-1a";
		String imageId = "ami-1b814f72";
		String groupName = cluster.getName().replaceAll(" ", "_");
		boolean monitoring = false;
		String keyName = "pruebasUnaCloud";
		
		List<UserInstance> userInstances = cluster.getUserinstances();
		for (UserInstance userInstance : userInstances) {
			String instanceType = "t1.micro";
			int quantity = 1;
			
			RunInstancesResult result = requestInstance(availabilityZone, groupName, imageId, instanceType, quantity, monitoring, keyName);
			Reservation reservation = result.getReservation();
			
			List<Instance> instances = reservation.getInstances();
			if(CollectionUtils.isNotEmpty(instances)){
				Instance ec2Instance = instances.get(0);
				String id = ec2Instance.getInstanceId();

			}
			
		}
		
		
		
		
	}

	/* (non-Javadoc)
	 * @see com.manuelvieda.unacloud.provider.ICloudProvider#turnOffCluster(com.manuelvieda.unacloud.entities.general.Cluster)
	 */
	@Override
	public void turnOffCluster(Cluster cluster) {
		
	}

	// -------------------------------------------------------------
	// PRIVATE FUNCTIONS / METHODS
	// -------------------------------------------------------------
	
	
	public void requestSpotInstance(){
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
	
	/**
	 * @param availabilityZone - The availability zone in which an Amazon EC2 instance runs.
	 * @param groupName - The name of the PlacementGroup in which an Amazon EC2 instance runs.
	 * @param String imageId - Unique ID of a machine image
	 * @param instanceType - Specifies the instance type for the launched instances.
	 * @param quantity - Number of instances to launch. If the value is more than Amazon EC2 can launch, 
	 * 			the largest possible number above minCount will be launched instead.
	 * @param monitoring - Enables monitoring for the instance.
	 * @param keyName
	 */
	public RunInstancesResult requestInstance(String availabilityZone, String groupName, String imageId, String instanceType, int quantity, boolean monitoring, String keyName){
		
		RunInstancesRequest requestInstance = new RunInstancesRequest();
		
		requestInstance.setImageId(imageId);
		requestInstance.setMaxCount(Integer.valueOf(quantity));
		requestInstance.setMinCount(Integer.valueOf(quantity));
		requestInstance.setInstanceType(instanceType);
		requestInstance.setMonitoring(monitoring);
		requestInstance.setDisableApiTermination(true);
		requestInstance.setKeyName(keyName);
		
		Collection<String> securityGroups = new ArrayList<String>();
		securityGroups.add("default");
		requestInstance.setSecurityGroups(securityGroups);
		
		Placement placement = new Placement();
		placement.setAvailabilityZone(availabilityZone);
		//placement.setGroupName(groupName);
		requestInstance.setPlacement(placement);
		
		RunInstancesResult result = ec2.runInstances(requestInstance);
		Reservation reservation = result.getReservation();
		
		System.out.println(reservation.toString());
		
		System.out.println("--> Owner ID: "+reservation.getOwnerId());
		System.out.println("--> Request ID: "+reservation.getRequesterId());
		
		List<Instance> instnaces = reservation.getInstances();
		for (Instance instance : instnaces) {
			System.out.println(" INSTANCE: ");
			System.out.println("   --> ID: "+instance.getInstanceId());
			System.out.println("   --> DNS Private Name: "+instance.getPrivateDnsName());
			System.out.println("   --> DNS Public Name : "+instance.getPublicDnsName());
			System.out.println("   --> Launch time: "+instance.getLaunchTime());
			System.out.println("   --> State: "+instance.getState().getName());
		}
		
		return result;
	}
	
	
	public void createCluster(String username, String clusterNamne){

		try{
			
			// Creates a Placement Group for the the user cluster.
			//The placement group name hast the following convention: <username>-<clustername>
			CreatePlacementGroupRequest placementRequest = new CreatePlacementGroupRequest(username+"-"+clusterNamne, AWSConstants.EC2_PLACEMENT_GROUP_CLUSTER_STRATEGY);
			ec2.createPlacementGroup(placementRequest);
			
			DescribeAvailabilityZonesResult result = ec2.describeAvailabilityZones();
			List<AvailabilityZone> zonas = result.getAvailabilityZones();
			for (AvailabilityZone availabilityZone : zonas) {
				System.out.println("Zona: "+availabilityZone.getZoneName()+"   Region: "+availabilityZone.getRegionName()+ "  Estado: "+availabilityZone.getState());
				
			}
			
			
			DescribeSecurityGroupsResult sGroups = ec2.describeSecurityGroups();
			List<SecurityGroup> securityGroups = sGroups.getSecurityGroups();
			for (SecurityGroup securityGroup : securityGroups) {
				System.out.println("ID: "+securityGroup.getGroupId()+"  Name: "+securityGroup.getGroupName());
			}
			
			
		}catch(AmazonClientException e){
			e.printStackTrace();
		}
	}
	
	
	
	
	private void retrieveCredentials(){
		try {
			System.out.println("--->> Obteniendo credenciales");
			URL url1 = this.getClass().getResource("/AwsCredentials.properties");
			System.out.println("--->>");
			System.out.println("--->>"+url1);
			System.out.println("--->>"+url1.getFile());
			credentials = new PropertiesCredentials(new File(url1.getFile()));
		//credentials = new PropertiesCredentials(AmazonEC2.class.getResourceAsStream(url1.getFile()));
			System.out.println("OK");
			//credentials = new PropertiesCredentials(this.getClass().getResourceAsStream(url1.getFile()));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
