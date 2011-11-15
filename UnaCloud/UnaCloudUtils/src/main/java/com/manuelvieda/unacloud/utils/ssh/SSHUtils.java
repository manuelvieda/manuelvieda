/**
 * UnaCloud - High Performance Computing on the Cloud
 * Manuel Eduardo Vieda Salomón (mail@manuelvieda.com)
 *
 * Creation: 23/10/2011
 * Project: UnaCloudUtils
 * Package: com.manuelvieda.unacloud.utils.ssh
 * File:    SSHUtils.java
 */
package com.manuelvieda.unacloud.utils.ssh;

import java.io.File;
import java.io.IOException;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UIKeyboardInteractive;
import com.jcraft.jsch.UserInfo;

import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.userauth.keyprovider.KeyProvider;

/**
 * Session Bean implementation class SSHUtils - Common functionalities for SSH Comunications
 * @author Manuel Eduardo Vieda Salomon  (mail@manuelvieda.com)
 * @version	1.0
 * @since	1.0
 */
@Stateless(mappedName = "SSHUtils")
@LocalBean
public class SSHUtils implements SSHUtilsLocal {

    /**
     * Default constructor. 
     */
    public SSHUtils() {
    }
    
    
    public SSHClient createConnection(String hostname, String username, String privateKeyPath){
    	
    	SSHClient client = null;
    	
		try {
			
			client = new SSHClient();
			KeyProvider keys = client.loadKeys(privateKeyPath);
			client.authPublickey(username, keys);
			client.connect(hostname);
			
			System.out.println("CONEXION ESTABLECIDA");
			
		} catch (IOException e) {
			System.out.println("ERROR -- "+e.getMessage());
			e.printStackTrace();
		}
		
		return client;
    	
    }
    
    public void createConnectionSSH(String hostname, String username, String privateKeyPath){
    	
    	try {
    		
    		JSch jsch = new JSch();
			jsch.addIdentity(privateKeyPath);
			Session session=jsch.getSession(username, hostname, 22);
			session.setConfig("StrictHostKeyChecking", "no");

			UserInfo ui= new MyUserInfo();
			session.setUserInfo(ui);
			session.connect();

			Channel channel=session.openChannel("shell");
			channel.setInputStream(System.in);
			channel.setOutputStream(System.out);

			channel.connect();
			
			System.out.println("Conexion: "+channel.isConnected());
			System.out.println("Conexion: "+session.isConnected());
			
			while(true){
				
			}

    	} catch (JSchException e) {
    		// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public static class MyUserInfo implements UserInfo, UIKeyboardInteractive{

		/* (non-Javadoc)
		 * @see com.jcraft.jsch.UserInfo#getPassphrase()
		 */
		@Override
		public String getPassphrase() {
			return "";
		}

		/* (non-Javadoc)
		 * @see com.jcraft.jsch.UserInfo#getPassword()
		 */
		@Override
		public String getPassword() {
			return "";
		}

		/* (non-Javadoc)
		 * @see com.jcraft.jsch.UserInfo#promptPassphrase(java.lang.String)
		 */
		@Override
		public boolean promptPassphrase(String arg0) {
			return false;
		}

		/* (non-Javadoc)
		 * @see com.jcraft.jsch.UserInfo#promptPassword(java.lang.String)
		 */
		@Override
		public boolean promptPassword(String arg0) {
			return false;
		}

		/* (non-Javadoc)
		 * @see com.jcraft.jsch.UserInfo#promptYesNo(java.lang.String)
		 */
		@Override
		public boolean promptYesNo(String arg0) {
			return false;
		}

		/* (non-Javadoc)
		 * @see com.jcraft.jsch.UserInfo#showMessage(java.lang.String)
		 */
		@Override
		public void showMessage(String arg0) {
			System.out.println(arg0);
		}

		/* (non-Javadoc)
		 * @see com.jcraft.jsch.UIKeyboardInteractive#promptKeyboardInteractive(java.lang.String, java.lang.String, java.lang.String, java.lang.String[], boolean[])
		 */
		@Override
		public String[] promptKeyboardInteractive(String arg0, String arg1,
				String arg2, String[] arg3, boolean[] arg4) {
			System.out.println("00000000000000000000000000000000000000000");
			return null;
		}
    	
    }
    

}
