/**
 * UnaCloud - High Performance Computing on the Cloud
 * Manuel Eduardo Vieda Salomón (mail@manuelvieda.com)
 *
 * Creation: 30/11/2011
 * Project: UnaCloudEJB
 * Package: com.manuelvieda.unacoud.client
 * File:    ThreadConnections.java
 */
package com.manuelvieda.unacoud.client;

import java.io.IOException;
import java.net.ServerSocket;

import com.manuelvieda.unacloud.repository.services.JobService;

/**
 *
 * @author Manuel Eduardo Vieda Salomon  (mail@manuelvieda.com)
 * @version
 * @since
 */
public class ThreadConnections extends Thread {
	
	
	private JobService jobService;
	
	/**
	 * 
	 */
	public ThreadConnections(JobService jobService) {
this.jobService = jobService;
}
	
	public void run(){
		
		ServerSocket serverSocket = null;
		 
		 try{
			 serverSocket = new ServerSocket(10020);
			 System.out.println("Listener created!");
			 
			 while(true){
				 System.out.println("Waiting for connection....");
				 new UnaCloudClientConnection(serverSocket.accept(), jobService);
			 }
			 
			 
		 }catch(IOException e){
			 e.printStackTrace();
			 
		 }finally{
			 try {
				serverSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			 
		 }
	}

}
