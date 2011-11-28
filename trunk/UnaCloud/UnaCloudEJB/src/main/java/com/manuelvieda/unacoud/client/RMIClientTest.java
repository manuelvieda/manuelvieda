package com.manuelvieda.unacoud.client;

import javax.ejb.Stateless;

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
    	return "---> Servidor responde "+msg;
    }

}
