package com.manuelvieda.unacoud.client;
import javax.ejb.Remote;

@Remote
public interface RMIClientTestRemote {
	
	
	public String eco(String msg);

}
