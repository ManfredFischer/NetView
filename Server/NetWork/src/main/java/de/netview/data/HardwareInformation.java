package de.netview.data;

import java.io.Serializable;

import de.netview.model.Hardware;

public class HardwareInformation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -344715559006434406L;
	private Long hid;
	private String hostname;
	private String ip;
	private String description;
	private String owner;
	private String aktivUsername;
	private String aktivDate;
	private String lastLogin;
	private String model;
	private String bs;
	private String cpu;
	private String ram;
	private String sn;
	private String categorie;
	private int location;
	private String department;
	
	public HardwareInformation(Hardware hardware) {
		this.hid = hardware.getHid();
		this.hostname = hardware.getHostname();
		this.ip = hardware.getIp();
		this.description = hardware.getDescription();
		this.owner = hardware.getOwner();
		this.aktivUsername = hardware.getAktivusername();
		this.aktivDate = hardware.getAktivdate();
		this.lastLogin = hardware.getLastlogin();
		this.model = hardware.getModel();
		this.bs = hardware.getBs();
		this.cpu = hardware.getCpu();
		this.ram = hardware.getRam();
		this.sn = hardware.getSn();
		this.categorie = hardware.getCategorie();
		this.department = hardware.getDepartment();
		this.setLocation(hardware.getLocation());
	}
	public Long getHid() {
		return hid;
	}
	public void setHid(Long hid) {
		this.hid = hid;
	}
	public String getHostname() {
		return hostname;
	}
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getAktivUsername() {
		return aktivUsername;
	}
	public void setAktivUsername(String aktivUsername) {
		this.aktivUsername = aktivUsername;
	}
	public String getAktivDate() {
		return aktivDate;
	}
	public void setAktivDate(String aktivDate) {
		this.aktivDate = aktivDate;
	}
	public String getLastLogin() {
		return lastLogin;
	}
	public void setLastLogin(String lastLogin) {
		this.lastLogin = lastLogin;
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
	public String getCategorie() {
		return categorie;
	}
	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((aktivDate == null) ? 0 : aktivDate.hashCode());
		result = prime * result + ((aktivUsername == null) ? 0 : aktivUsername.hashCode());
		result = prime * result + ((bs == null) ? 0 : bs.hashCode());
		result = prime * result + ((categorie == null) ? 0 : categorie.hashCode());
		result = prime * result + ((cpu == null) ? 0 : cpu.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((hid == null) ? 0 : hid.hashCode());
		result = prime * result + ((hostname == null) ? 0 : hostname.hashCode());
		result = prime * result + ((ip == null) ? 0 : ip.hashCode());
		result = prime * result + ((lastLogin == null) ? 0 : lastLogin.hashCode());
		result = prime * result + ((model == null) ? 0 : model.hashCode());
		result = prime * result + ((owner == null) ? 0 : owner.hashCode());
		result = prime * result + ((ram == null) ? 0 : ram.hashCode());
		result = prime * result + ((sn == null) ? 0 : sn.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HardwareInformation other = (HardwareInformation) obj;
		if (aktivDate == null) {
			if (other.aktivDate != null)
				return false;
		} else if (!aktivDate.equals(other.aktivDate))
			return false;
		if (aktivUsername == null) {
			if (other.aktivUsername != null)
				return false;
		} else if (!aktivUsername.equals(other.aktivUsername))
			return false;
		if (bs == null) {
			if (other.bs != null)
				return false;
		} else if (!bs.equals(other.bs))
			return false;
		if (categorie == null) {
			if (other.categorie != null)
				return false;
		} else if (!categorie.equals(other.categorie))
			return false;
		if (cpu == null) {
			if (other.cpu != null)
				return false;
		} else if (!cpu.equals(other.cpu))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (hid == null) {
			if (other.hid != null)
				return false;
		} else if (!hid.equals(other.hid))
			return false;
		if (hostname == null) {
			if (other.hostname != null)
				return false;
		} else if (!hostname.equals(other.hostname))
			return false;
		if (ip == null) {
			if (other.ip != null)
				return false;
		} else if (!ip.equals(other.ip))
			return false;
		if (lastLogin == null) {
			if (other.lastLogin != null)
				return false;
		} else if (!lastLogin.equals(other.lastLogin))
			return false;
		if (model == null) {
			if (other.model != null)
				return false;
		} else if (!model.equals(other.model))
			return false;
		if (owner == null) {
			if (other.owner != null)
				return false;
		} else if (!owner.equals(other.owner))
			return false;
		if (ram == null) {
			if (other.ram != null)
				return false;
		} else if (!ram.equals(other.ram))
			return false;
		if (sn == null) {
			if (other.sn != null)
				return false;
		} else if (!sn.equals(other.sn))
			return false;
		return true;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public int getLocation() {
		return location;
	}
	public void setLocation(int location) {
		this.location = location;
	}
	
	

}
