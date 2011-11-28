package com.manuelvieda.unacoud.client;
import javax.ejb.Local;

@Local
public interface RMIClientTestLocal {
	
	public String eco(String msg);

}
