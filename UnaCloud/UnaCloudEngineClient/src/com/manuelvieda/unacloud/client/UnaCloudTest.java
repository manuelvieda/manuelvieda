/**
 * UnaCloud - High Performance Computing on the Cloud
 * Manuel Eduardo Vieda Salomón (mail@manuelvieda.com)
 *
 * Creation: 29/11/2011
 * Project: RMITest
 * Package: com.manuelvieda.unacloud.client
 * File:    UnaCloudTest.java
 */
package com.manuelvieda.unacloud.client;

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author Manuel Eduardo Vieda Salomon  (mail@manuelvieda.com)
 * @version
 * @since
 */
public class UnaCloudTest extends UnicastRemoteObject implements IUnaCloudClient {

	private static final long serialVersionUID = 1L;
	
	private boolean ocupado = false;

	/**
	 * @throws RemoteException
	 */
	protected UnaCloudTest() throws RemoteException {
		super();
	}

	/* (non-Javadoc)
	 * @see com.manuelvieda.unacloud.client.IUnaCloudClient#sayHello()
	 */
	@Override
	public String sayHello() throws RemoteException {
		// TODO Auto-generated method stub
		return "Server says hello";
	}
	
	/* (non-Javadoc)
	 * @see com.manuelvieda.unacloud.client.IUnaCloudClient#startJob(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void startJob(String appUrl, String appName, String appParams, int jobId) {
		
		
		try {
			ocupado = true;
			String wgetCommand = "gwet "+appUrl;
			Process process = Runtime.getRuntime().exec(wgetCommand);
			int returnCode = process.waitFor();
			if(returnCode==0){
				String runCoomand = "java -jar "+appName+" "+appParams;
				Process processApp = Runtime.getRuntime().exec(runCoomand);
				int returnCodeApp = processApp.waitFor();
				
				String resp = "";
				
				if(returnCodeApp==0){
					resp= "PRIMO";
				}if(returnCodeApp==1)
					resp = "NO PRIMO";
				else{
					resp = "ERROR";
				}
				
				System.out.println(resp);
					
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally{
			ocupado = false;
		}
		
	}
	
	 public static void main(String args[]) {
		 
		 System.setSecurityManager (new RMISecurityManager() {
			    public void checkConnect (String host, int port) {}
			    public void checkConnect (String host, int port, Object context) {}
			  });
		
		try {
			
			UnaCloudTest obj = new UnaCloudTest();
			LocateRegistry.createRegistry(4041);
			Naming.rebind("rmi://"+args[0]+":4041/UnaCloudClient", obj);
			System.out.println("Application Started");
			
		} catch (Exception e) {
		    System.out.println("UnaCloudClient err: " + e.getMessage());
		    e.printStackTrace();
		}
		
		 		 
	 }
	 
	 /**
	 * @return the ocupado
	 */
	public boolean isOcupado() {
		return ocupado;
	}

	

}
