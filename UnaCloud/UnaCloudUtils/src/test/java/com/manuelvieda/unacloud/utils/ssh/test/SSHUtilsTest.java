/**
 * UnaCloud - High Performance Computing on the Cloud
 * Manuel Eduardo Vieda Salomón (mail@manuelvieda.com)
 *
 * Creation: 13/11/2011
 * Project: UnaCloudUtils
 * Package: com.manuelvieda.unacloud.utils.ssh.test
 * File:    SSHUtilsTest.java
 */
package com.manuelvieda.unacloud.utils.ssh.test;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.ejb.EJB;

import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.common.IOUtils;
import net.schmizz.sshj.connection.ConnectionException;
import net.schmizz.sshj.connection.channel.direct.Session;
import net.schmizz.sshj.connection.channel.direct.Session.Command;
import net.schmizz.sshj.transport.TransportException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.internal.runners.statements.Fail;

import com.manuelvieda.unacloud.utils.ssh.SSHUtils;

/**
 * SSHUtils testing class
 * @author Manuel Eduardo Vieda Salomon  (mail@manuelvieda.com)
 * @version	1.0
 * @since	1.0
 */
public class SSHUtilsTest {

	@EJB
	private SSHUtils sshUtils;
	
	private String hostname;
	private String username;
	private String privateKeyPath;
	
	/**
	 * Setups the test enviroment
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		sshUtils = new SSHUtils();
		
		hostname = "157.253.236.184";
		hostname = "ec2-50-16-32-226.compute-1.amazonaws.com";
		
		username = "manuelvieda";
		username = "ec2-user";
		
		privateKeyPath = "C:\\Users\\ManuelVieda\\Desktop\\EC2\\pruebasUnaCloud.pem";
	}
	
	/**
	 * Tears down the test enviroment
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testSSHConnectionJsch(){
		sshUtils.createConnectionSSH(hostname, username, privateKeyPath);
	}

	public void testSSHConnection() {
		
		SSHClient clienteSSH = sshUtils.createConnection(hostname, username, privateKeyPath);
		if(clienteSSH==null)
			fail("Can not create a SSH connection with private/public key authentication method");
		
		try {
            final Session session = clienteSSH.startSession();
            try {
                final Command cmd = session.exec("ping -c 1 google.com");
                System.out.println(IOUtils.readFully(cmd.getInputStream()).toString());
                cmd.join(5, TimeUnit.SECONDS);
                System.out.println("\n** exit status: " + cmd.getExitStatus());
            } catch (IOException e) {
				e.printStackTrace();
			} finally {
                session.close();
            }
        } catch (ConnectionException e) {
			e.printStackTrace();
		} catch (TransportException e) {
			e.printStackTrace();
		} finally {
            try {
				clienteSSH.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
	}
}
