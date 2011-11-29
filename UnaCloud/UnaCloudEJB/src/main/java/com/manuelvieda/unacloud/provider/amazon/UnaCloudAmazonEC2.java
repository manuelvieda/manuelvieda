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

import javax.ejb.EJB;
import javax.ejb.Stateless;


import org.apache.commons.collections.CollectionUtils;

import com.manuelvieda.unacloud.entities.general.Cluster;
import com.manuelvieda.unacloud.entities.general.UserInstance;
import com.manuelvieda.unacloud.provider.ICloudProvider;
import com.manuelvieda.unacloud.repository.constants.AWSConstants;
import com.manuelvieda.unacloud.repository.dao.UserInstanceDao;
import com.manuelvieda.unacloud.utils.ssh.SSHUtils;
import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.*;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.AvailabilityZone;
import com.amazonaws.services.ec2.model.CreatePlacementGroupRequest;
import com.amazonaws.services.ec2.model.DescribeAvailabilityZonesResult;
import com.amazonaws.services.ec2.model.DescribeInstancesRequest;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.DescribeSecurityGroupsResult;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.InstanceStateChange;
import com.amazonaws.services.ec2.model.LaunchSpecification;
import com.amazonaws.services.ec2.model.Placement;
import com.amazonaws.services.ec2.model.RequestSpotInstancesRequest;
import com.amazonaws.services.ec2.model.Reservation;
import com.amazonaws.services.ec2.model.RunInstancesRequest;
import com.amazonaws.services.ec2.model.RunInstancesResult;
import com.amazonaws.services.ec2.model.SecurityGroup;
import com.amazonaws.services.ec2.model.StopInstancesRequest;
import com.amazonaws.services.ec2.model.StopInstancesResult;

/**
 * Class that implements the functionality of Amazon Elastic Cloud Computing 
 * @author Manuel Eduardo Vieda Salomon  (mail@manuelvieda.com)
 * @version	1.0
 * @since	1.0
 */

@Stateless (name="unaCloudAmazonEC2Bean")
public class UnaCloudAmazonEC2 implements ICloudProvider {
	
	@EJB
	private SSHUtils sshUtils;
	
	@EJB
	private UserInstanceDao userInstanceDao;
	
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
		 sshUtils = new SSHUtils();
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
				String dnsName = ec2Instance.getPublicDnsName();
				userInstance.setIdentifier(id);
				userInstanceDao.updateInstanteMonitoringInfo(userInstance.getId(), id, 16, dnsName);
				
			}
			
		}
		
		
		boolean termino = false;
		
		while(!termino){
			
			termino = true;
			// Sleep
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			// Obtiene la informacion del estado de las instancias lanzadas
			for (UserInstance userInstance : userInstances) {
				
				if(userInstance.getDnsName()==null || "".equals(userInstance.getDnsName())){
					termino = false;
					
					Collection<String> ids = new ArrayList<String>();
					ids.add(userInstance.getIdentifier());
					DescribeInstancesRequest request = new DescribeInstancesRequest();
					request.setInstanceIds(ids);
					request.setRequestCredentials(credentials);
					DescribeInstancesResult result = ec2.describeInstances(request);
					
					String publicDNSName = result.getReservations().get(0).getInstances().get(0).getPublicDnsName();
					if(publicDNSName!=null && !publicDNSName.equals("")){
						userInstanceDao.updateInstanteMonitoringInfo(userInstance.getId(), userInstance.getIdentifier(), 16, publicDNSName);
						userInstance.setDnsName(publicDNSName);
						
						// Install UnaCLoudClient
						
						System.out.println(" ---> Realiando conexion con "+publicDNSName);
						
						try {
							for (int i = 0; i < 30; i++) {
								System.out.println("---->Sleeping");
								Thread.sleep(2000);
							}
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						
						String hostname = publicDNSName;
						String username = "ec2-user";
						String privateKeyPath = "C:\\Users\\ManuelVieda\\Desktop\\EC2\\pruebasUnaCloud.pem";
						
						String command = "wget https://s3.amazonaws.com/UnaCloudRespository/Applications/java.policy; "+
								"wget https://s3.amazonaws.com/UnaCloudRespository/Applications/UnaCloudEngineClient.jar; "+
								"java -jar UnaCloudEngineClient.jar -Djava.security.policy=java.policy";
						
						sshUtils = new SSHUtils();
						sshUtils.createConnectionSSH(hostname, username, privateKeyPath, command);
						
					}
						
				}
				
				
			}
			
		}
		System.out.println("Termino de crear Cluster en AMAZON EC2");

		
		
	}

	/* (non-Javadoc)
	 * @see com.manuelvieda.unacloud.provider.ICloudProvider#turnOffCluster(com.manuelvieda.unacloud.entities.general.Cluster)
	 */
	@Override
	public void turnOffCluster(Cluster cluster) {
		
		List<UserInstance> userInstances = cluster.getUserinstances();
		
		ArrayList<String> instanceIds = new ArrayList<String>();
		for (UserInstance userInstance : userInstances) {
			instanceIds.add(userInstance.getIdentifier());
		}
		
		StopInstancesRequest request = new StopInstancesRequest();
		request.setInstanceIds(instanceIds);
		
		StopInstancesResult  result = ec2.stopInstances(request);
		
		List<InstanceStateChange>  stopppingInstances = result.getStoppingInstances();
		for (InstanceStateChange instanceStateChange : stopppingInstances) {
			System.out.println("--> Terminandom instancia: "+instanceStateChange.getInstanceId());
		}
		
		
		for (UserInstance userInstance : userInstances) {
			userInstanceDao.updateInstanteMonitoringInfo(userInstance.getId(), "", 15, "");
		}
		

		// Terminate each instance
		
		
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
		//RequestSpotInstancesResult requestResult = ec2.requestSpotInstances(requestRequest);
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
