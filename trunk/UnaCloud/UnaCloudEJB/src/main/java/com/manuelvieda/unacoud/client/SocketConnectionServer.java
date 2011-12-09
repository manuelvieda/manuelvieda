package com.manuelvieda.unacoud.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;

/**
 * Session Bean implementation class SocketConnectionServer
 */
@Singleton
@LocalBean
public class SocketConnectionServer {
	
	@EJB
	private UnaCloudScketServerThread connections;

    /**
     * Default constructor. 
     */
    public SocketConnectionServer() {
    	
    }
    
    @PostConstruct
    public void init(){
    	connections.check();
    	
    }
    
    public boolean checkMachine(String hostname, int port){
    	boolean resp = false;
    	
    	Socket clientSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;
        
        
        try {
        	clientSocket = new Socket(hostname, port);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            
            String msg = null;
            
            out.println("CHECK");
            try {
    			msg = in.readLine();
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
            
            if(msg!=null && msg.equalsIgnoreCase("ALIVE")){
            	resp = true;
            }
            
            
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: " + hostname);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: " + hostname);
        }
        
        
        
        return resp;
    	
    }
    
    /**
     * 
     * @param hostname
     * @param port
     * @param urlApplication
     * @param parameters
     * @return
     */
    public boolean sendJob(String hostname, int port, String urlApplication, String idJob, String parameters) {
    	boolean resp = false;
    	
    	Socket clientSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;
        
        System.out.println("PREPARANDO CONEXION CON MAQUINA "+hostname);
        
        try {
        	clientSocket = new Socket(hostname, port);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: " + hostname);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: " + hostname);
        }
        
        System.out.println("CONEXION ESTABLECIDA");
        
        String msg = null;
       
        
        System.out.println("ENVIANDO COMADNOS");
        
        
        try {
        	out.println("JOB");
        	msg = in.readLine();
        	if(msg!=null)
        		out.println(idJob);
        	System.out.println(msg);
        	
        	msg = in.readLine();
        	if(msg!=null)
            	out.println(urlApplication);
        	System.out.println(msg);
        	
        	msg = in.readLine();
        	if(msg!=null)
        		out.println(parameters);
        	System.out.println(msg);
        	
			msg = in.readLine();
			System.out.println(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}

        System.out.println("Trabajo enviado "+msg);
        if(msg!=null && msg.equalsIgnoreCase("JOB-OK")){
        	resp = true;
        }
        
        return resp;
    }


}
