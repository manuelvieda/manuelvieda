/**
 * UnaCloud - High Performance Computing on the Cloud
 * Manuel Eduardo Vieda Salomón (mail@manuelvieda.com)
 *
 * Creation: 30/11/2011
 * Project: UnaCloudEngineClient
 * Package: com.manuelvieda.unacloud.client
 * File:    UnaCloudSocketServer.java
 */
package com.manuelvieda.unacloud.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *
 * @author Manuel Eduardo Vieda Salomon  (mail@manuelvieda.com)
 * @version
 * @since
 */
public class UnaCloudSocketServer extends Thread{

	
	private final static String HOSTNAME = "manuelvieda.homedns.org";
	//private final static String HOSTNAME = "157.253.236.196";
	private final static int PORT = 10020;
	
	protected Socket clientSocket;
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		 ServerSocket serverSocket = null;
		 
		 try{
			 serverSocket = new ServerSocket(10010);
			 System.out.println("Listener created!");
			 
			 while(true){
				 System.out.println("Waiting for connection....");
				 new UnaCloudSocketServer(serverSocket.accept());
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
	
	
	/**
	 * 
	 */
	public UnaCloudSocketServer(Socket socketCliente) {
		
		clientSocket = socketCliente;
	    start();
	}
	
	public void run(){

		System.out.println ("New Communication Thread Started");
		try { 
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true); 
			BufferedReader in = new BufferedReader( new InputStreamReader( clientSocket.getInputStream())); 

			String inputLine; 

			while ((inputLine = in.readLine()) != null){
				
				// Chech machine method
				if(inputLine.equals("CHECK")){
					out.println("ALIVE");
					break;
				}
				
				// Chech machine method
				else if(inputLine.equals("JOB")){
					out.println("ACCEPT JOB");
					
					String idJob;
					String urlApp;
					String params;
					
					String msg = in.readLine();
					if(msg!=null){
						 idJob = msg;
						 out.println("OK");
					}else{
						out.println("JOB-ERROR");
						break;
					}
					
					 msg = in.readLine();
					if(msg!=null){
						 urlApp = msg;
						 out.println("OK");
					}else{
						out.println("JOB-ERROR");
						break;
					}
					
					msg = in.readLine();
					if(msg!=null){
						params = msg;
					}else{
						out.println("JOB-ERROR");
						break;
					}
					out.println("JOB-OK");
					
					lanzarAplicacion(idJob, urlApp, params);
					break;
				}
				else{
					out.println("ERROR");
				}
				
			}
			
			out.close(); 
			in.close(); 
			clientSocket.close(); 
		} 
		catch (IOException e) 
		{ 
			System.err.println("Problem with Communication Server");
			System.exit(1); 
		} 
	}
		
	
	
	public void lanzarAplicacion(String idJob, String appUrl, String appParams){
		
		try{
			String appName = appUrl.substring(appUrl.lastIndexOf("/")+1);
			String wgetCommand = "wget "+appUrl;
			System.out.println("Ejecutando comando "+wgetCommand);
			Process process = Runtime.getRuntime().exec(wgetCommand);
			int returnCode = process.waitFor();
			if(returnCode==0){
				String runCoomand = "java -jar "+appName+" "+appParams;
				
				System.out.println("Ejecutando comando "+runCoomand);
				Process processApp = Runtime.getRuntime().exec(runCoomand);
				int returnCodeApp = processApp.waitFor();
				System.out.println("Return code: "+returnCodeApp);

				String resp = "";

				if(returnCodeApp==0){
					resp= "NO PRIMO";
				}else if(returnCodeApp==1)
					resp = "PRIMO";
				else{
					resp = "ERROR";
				}

				System.out.println(resp);
				
				// Conecta con servidor para almacenar respouesta
				
				Socket clientSocket = null;
		        PrintWriter out = null;
		        BufferedReader in = null;
		        
		        
		        try {
		        	clientSocket = new Socket(HOSTNAME, PORT);
		            out = new PrintWriter(clientSocket.getOutputStream(), true);
		            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		        } catch (UnknownHostException e) {
		            System.err.println("Don't know about host: " + HOSTNAME);
		        } catch (IOException e) {
		            System.err.println("Couldn't get I/O for the connection to: " + HOSTNAME);
		        }
		        
		        out.println("JOB-"+idJob+"-"+resp);
		        
	    		out.close();
				in.close();
				clientSocket.close();

			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally{
		}
	}

}
