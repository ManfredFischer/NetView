package de.netview.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "DISTRIBUTESOFTWARE")
public class DistributeSoftware implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3233569956946841132L;
	private long dsid;
	private String displayname;
	private String installconent;
	private String description;
	private boolean defaultsoftware;
	private boolean distribute;
	private boolean removebydeleting;
	private boolean deleted;
	private boolean msi;
	
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
	public long getDsid() {
		return dsid;
	}
	
	public void setDsid(long dsid) {
		this.dsid = dsid;
	}
	
	public String getDisplayname() {
		return displayname;
	}
	public void setDisplayname(String displayname) {
		this.displayname = displayname;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isDefaultsoftware() {
		return defaultsoftware;
	}
	public void setDefaultsoftware(boolean defaultsoftware) {
		this.defaultsoftware = defaultsoftware;
	}
	public boolean isDistribute() {
		return distribute;
	}
	public void setDistribute(boolean distribute) {
		this.distribute = distribute;
	}
	public boolean isRemovebydeleting() {
		return removebydeleting;
	}
	public void setRemovebydeleting(boolean removebydeleting) {
		this.removebydeleting = removebydeleting;
	}
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public boolean isMsi() {
		return msi;
	}

	public void setMsi(boolean msi) {
		this.msi = msi;
	}

	public String getInstallconent() {
		return installconent;
	}

	public void setInstallconent(String installconent) {
		this.installconent = installconent;
	}	
	
}
