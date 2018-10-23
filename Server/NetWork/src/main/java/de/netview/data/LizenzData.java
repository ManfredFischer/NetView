package de.netview.data;

import java.io.Serializable;

public class LizenzData implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2333159285303268420L;
	private long lid;
	private String lizenz;
	private String lizenzkey;
	private String message;
	
	public long getLid() {
		return lid;
	}
	public void setLid(long lid) {
		this.lid = lid;
	}
	public String getLizenz() {
		return lizenz;
	}
	public void setLizenz(String lizenz) {
		this.lizenz = lizenz;
	}
	public String getLizenzkey() {
		return lizenzkey;
	}
	public void setLizenzkey(String lizenzkey) {
		this.lizenzkey = lizenzkey;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	

}
