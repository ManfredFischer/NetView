package de.netview.data;

import java.io.Serializable;

public class SoftwareData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 29050014583517114L;
	private long sid;
	private String name;
	
	public long getSid() {
		return sid;
	}
	public void setSid(long sid) {
		this.sid = sid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	

}
