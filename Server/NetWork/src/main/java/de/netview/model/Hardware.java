package de.netview.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "HARDWARE")
public class Hardware {

	private long hid;
	private String hostname;
	private String owner;
	private String aktivusername;
	private String aktivdate;
	private String lastlogin;
	private String model;
	private String bs;
	private String cpu;
	private String ram;
	private String sn;
	private List<Software> software = new ArrayList<Software>();
	private List<Lizenz> lizenz = new ArrayList<Lizenz>();
	
	@ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                CascadeType.PERSIST,
                CascadeType.MERGE
            })
    @JoinTable(name = "hardware_lizenz",
            joinColumns = { @JoinColumn(name = "hid") },
            inverseJoinColumns = { @JoinColumn(name = "lid") })
	public List<Lizenz> getLizenz() {
		return lizenz;
	}
	
	public void addLizenz(Lizenz lizenz) {
		this.lizenz.add(lizenz);
	}
	
	public void clearLizenz() {
		this.lizenz.clear();
	}

	public void setLizenz(List<Lizenz> lizenz) {
		this.lizenz = lizenz;
	}

	@ManyToMany(fetch = FetchType.LAZY,
	            cascade = {
	                CascadeType.PERSIST,
	                CascadeType.MERGE
	            })
	    @JoinTable(name = "hardware_software",
	            joinColumns = { @JoinColumn(name = "hid") },
	            inverseJoinColumns = { @JoinColumn(name = "sid") })
	public List<Software> getSoftware() {
		return software;
	}
	 
	public void setSoftware(List<Software> software) {
		this.software = software;
	}
	 
	public void addSoftware(Software software) {
		this.software.add(software);
	}
	
    @Column(unique = true,name = "hostname", nullable = false)
	public String getHostname() {
		return hostname;
	}
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	
	public String getBs() {
		return bs;
	}
	public void setBs(String bs) {
		this.bs = bs;
	}
	public String getCpu() {
		return cpu;
	}
	public void setCpu(String cpu) {
		this.cpu = cpu;
	}
	public String getRam() {
		return ram;
	}
	public void setRam(String ram) {
		this.ram = ram;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false, precision = 10)
	public long getHid() {
		return hid;
	}

	public void setHid(long hid) {
		this.hid = hid;
	}

	public String getAktivdate() {
		return aktivdate;
	}

	public void setAktivdate(String aktivdate) {
		this.aktivdate = aktivdate;
	}

	public String getAktivusername() {
		return aktivusername;
	}

	public void setAktivusername(String aktivusername) {
		this.aktivusername = aktivusername;
	}

	public String getLastlogin() {
		return lastlogin;
	}

	public void setLastlogin(String lastlogin) {
		this.lastlogin = lastlogin;
	}

	
	
	

}
