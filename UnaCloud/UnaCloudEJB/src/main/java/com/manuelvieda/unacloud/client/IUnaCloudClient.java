/**
 * UnaCloud - High Performance Computing on the Cloud
 * Manuel Eduardo Vieda Salomón (mail@manuelvieda.com)
 *
 * Creation: 29/11/2011
 * Project: RMITest
 * Package: com.manuelvieda.unacloud.client
 * File:    IUnaCloudClient.java
 */
package com.manuelvieda.unacloud.client;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Manuel Eduardo Vieda Salomon  (mail@manuelvieda.com)
 * @version
 * @since
 */
public interface IUnaCloudClient extends Remote {
	
	String sayHello() throws RemoteException;

}
