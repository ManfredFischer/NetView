package de.netview.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import de.netview.model.Hardware;
import de.netview.model.Lizenz;
import de.netview.model.Software;

public class HardwareData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -344715559006434406L;
	private String hostname;
	private String owner;
	private String aktivUsername;
	private String aktivDate;
	private String lastLogin;
	private String model;
	private String bs;
	private String cpu;
	private String ram;
	private String sn;
	private List<LizenzData> lizenz = new ArrayList<LizenzData>();
	private List<SoftwareData> software = new ArrayList<SoftwareData>();

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

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
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

	public List<LizenzData> getLizenz() {
		return lizenz;
	}

	public void setLizenz(List<Lizenz> lizenz) {
		for (Lizenz lizenzInfo : lizenz) {
			LizenzData lizenzData = new LizenzData();
			lizenzData.setLid(lizenzInfo.getLid());
			lizenzData.setLizenz(lizenzInfo.getLizenz());
			lizenzData.setLizenzkey(lizenzInfo.getLizenzkey());
			lizenzData.setMessage(lizenzInfo.getMessage());
			this.lizenz.add(lizenzData);
		}
	}

	public List<SoftwareData> getSoftware() {
		return software;
	}

	public void setSoftware(List<Software> software) {
		for (Software softwareInfo : software) {
			SoftwareData softwareData = new SoftwareData();
			softwareData.setName(softwareInfo.getName());
			softwareData.setSid(softwareInfo.getSid());
			this.software.add(softwareData);
		}
	}

	public String getAktivDate() {
		return aktivDate;
	}

	public void setAktivDate(String aktivDate) {
		this.aktivDate = aktivDate;
	}
	
	@JsonIgnore
	public void wrapperHardware(Hardware hardware) {
		this.setBs(hardware.getBs());
		this.setCpu(hardware.getCpu());
		this.setHostname(hardware.getHostname());
		this.setModel(hardware.getModel());
		this.setRam(hardware.getRam());
		this.setSn(hardware.getSn());
		this.setAktivUsername(hardware.getAktivusername());
		this.setAktivDate(hardware.getAktivdate());
		this.setOwner(hardware.getOwner());
		this.setLizenz(hardware.getLizenz());
		this.setSoftware(hardware.getSoftware());
		this.setLastLogin(hardware.getLastlogin());
	}

	public String getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(String lastLogin) {
		this.lastLogin = lastLogin;
	}

}
