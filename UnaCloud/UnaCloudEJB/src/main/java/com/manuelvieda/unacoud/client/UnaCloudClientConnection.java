/**
 * UnaCloud - High Performance Computing on the Cloud
 * Manuel Eduardo Vieda Salomón (mail@manuelvieda.com)
 *
 * Creation: 30/11/2011
 * Project: UnaCloudEJB
 * Package: com.manuelvieda.unacoud.client
 * File:    UnaCloudClientConnection.java
 */
package com.manuelvieda.unacoud.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.ejb.EJB;

import com.manuelvieda.unacloud.repository.services.JobService;

/**
 *
 * @author Manuel Eduardo Vieda Salomon  (mail@manuelvieda.com)
 * @version
 * @since
 */
public class UnaCloudClientConnection extends Thread{
	
	protected Socket clientSocket;
	
	protected JobService jobService;
	/**
	 * @param accept
	 */
	public UnaCloudClientConnection(Socket accept, JobService jobService) {
		clientSocket = accept;
		this.jobService = jobService;
	    start();
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	public void run(){
		
		
		try { 
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true); 
			BufferedReader in = new BufferedReader( new InputStreamReader( clientSocket.getInputStream())); 

			String inputLine; 

			while ((inputLine = in.readLine()) != null){ 
				
				System.out.println("--> RECIBIDO: "+inputLine);
				
				if(inputLine.startsWith("JOB")){
					System.out.println("--> llego respuesta de trabajo "+inputLine+"  //  "+inputLine.split("-").length);
					String[] jobResult = inputLine.split("-");
					if(jobResult.length==3)
						jobService.updateJobResult(Integer.parseInt(jobResult[1]), jobResult[2]);
					
				}
				
				// Chech machine method
				if(inputLine.equals("CHECK")){
					out.println("ALIVE");
					break;
				}
				
			}
			
			out.close(); 
			in.close(); 
			clientSocket.close();
			
			
		}catch (IOException e){ 
			System.err.println("Problem with Communication Server");
			e.printStackTrace();
		}
		
		System.out.println("programa terminado");
	}
	
	
}
