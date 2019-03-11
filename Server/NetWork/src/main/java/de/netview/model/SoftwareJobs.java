package de.netview.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "HARDWARE_DISTRIBUTESOFTWARE")
public class SoftwareJobs implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7101750203531820286L;
	private long hid;
	private long dsid;
	private String date;
	
	@Id
	public long getHid() {
		return hid;
	}
	public void setHid(long hid) {
		this.hid = hid;
	}
	
	@Id
	public long getDsid() {
		return dsid;
	}
	public void setDsid(long dsid) {
		this.dsid = dsid;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	

}
