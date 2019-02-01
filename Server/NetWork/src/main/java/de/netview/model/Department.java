package de.netview.model;

import java.io.Serializable;

public class Department implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1089319279002474975L;
	public Long did;
	public String displayname;
	public String name;
	public String position;
	
	
	public Long getDid() {
		return did;
	}
	public void setDid(Long did) {
		this.did = did;
	}
	public String getDisplayname() {
		return displayname;
	}
	public void setDisplayname(String displayname) {
		this.displayname = displayname;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
}
