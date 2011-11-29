package com.manuelvieda.unacoud.client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.ejb.Stateless;

import com.manuelvieda.unacloud.client.IUnaCloudClient;

/**
 * Session Bean implementation class RMIClientTest
 */
@Stateless(mappedName="ejb/RMIClientTest", name="ejb/RMIClientTest")
public class RMIClientTest implements RMIClientTestRemote, RMIClientTestLocal {

    /**
     * Default constructor. 
     */
    public RMIClientTest() {
        // TODO Auto-generated constructor stub
    }
    
    public String eco(String msg){
    	
    	String msgg = " -- ";
    	
    	try {
    		System.out.println("----> LLAMADO A CLIENTE ---- ");
    		IUnaCloudClient obj = null;
			obj = (IUnaCloudClient)Naming.lookup("rmi://127.0.0.1:4041/UnaCloudClient");
			msgg += obj.sayHello();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
    	
    	
    	
    	return "---> Servidor responde "+msgg;
    }

}
