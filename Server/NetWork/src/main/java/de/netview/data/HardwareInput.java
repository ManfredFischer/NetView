package de.netview.data;

import java.io.Serializable;

import de.netview.model.Hardware;

public class HardwareInput implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5609263154166712061L;
	
	private String hostname;
	private String oldHostname;
	private String owner;
	private Hardware hardware;
	
	public String getHostname() {
		return hostname;
	}
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
	public String getOldHostname() {
		return oldHostname;
	}
	public void setOldHostname(String oldHostname) {
		this.oldHostname = oldHostname;
	}
	public Hardware getHardware() {
		return hardware;
	}
	public void setHardware(Hardware hardware) {
		this.hardware = hardware;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	
	
	

}
