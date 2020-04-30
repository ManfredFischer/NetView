package de.netview.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import de.netview.config.BeanUtil;
import de.netview.data.enums.HardwareStatus;
import de.netview.function.impl.DateUtil;
import de.netview.model.*;
import de.netview.service.ILDAPService;

public class HardwareData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -344715559006434406L;
	private Long hid;
	private String hostname;
	private String ip;
	private String description;
	private String date;
	private String lastLogin;
	private String model;
	private String bs;
	private String cpu;
	private String ram;
	private String sn;
	private HardwareStatus status;
	private String categorie;
	private int ownerLocation;
	private int aktivLocation;
	private String department;
	private String encodingkey;
	private String encodingname;
	private ADUserData ownerInformation;
	private ADUserData inUseInformation;
	private Boolean verliehen;
	private String verliehenAn;
	private String verliehenBis;
	private String verliehenTel;
	private String verliehenMobile;

	private List<Document> documents = new ArrayList<>();
	private List<LizenzData> lizenz = new ArrayList<LizenzData>();
	private List<SoftwareData> software = new ArrayList<SoftwareData>();
	private List<Changelog> changelogList = new ArrayList<Changelog>();

	public List<Document> getDocuments() {
		return documents;
	}

	public void setDocuments(List<Document> documents) {
		this.documents = documents;
	}

	public String getDate() {
		return date;
	}

	public Boolean getVerliehen() {
		return verliehen;
	}

	public void setVerliehen(Boolean verliehen) {
		this.verliehen = verliehen;
	}

	public String getVerliehenAn() {
		return verliehenAn;
	}

	public void setVerliehenAn(String verliehenAn) {
		this.verliehenAn = verliehenAn;
	}

	public String getVerliehenBis() {
		return verliehenBis;
	}

	public void setVerliehenBis(String verliehenBis) {
		this.verliehenBis = verliehenBis;
	}

	public String getVerliehenTel() {
		return verliehenTel;
	}

	public void setVerliehenTel(String verliehenTel) {
		this.verliehenTel = verliehenTel;
	}

	public String getVerliehenMobile() {
		return verliehenMobile;
	}

	public void setVerliehenMobile(String verliehenMobile) {
		this.verliehenMobile = verliehenMobile;
	}

	public HardwareStatus getStatus() {
		return status;
	}

	public void setStatus(HardwareStatus status) {
		this.status = status;
	}

	public HardwareData() {
		super();
	}

	public HardwareData(ADUserData ownerInformation, ADUserData inUseInformation) {
		super();
		this.ownerInformation = ownerInformation;
		this.inUseInformation = inUseInformation;
	}

	public List<Changelog> getChangelogList() {
		return changelogList;
	}

	public void setChangelogList(List<Changelog> changelogList) {
		this.changelogList = changelogList;
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
			lizenzData.setName(lizenzInfo.getName());
			lizenzData.setKey(lizenzInfo.getKey());
			lizenzData.setCategorie(lizenzInfo.getCategorie());
			lizenzData.setState(lizenzInfo.getState());
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
	
	@JsonIgnore
	public void wrapperHardware(Hardware hardware) {
		this.setHid(hardware.getHid());
		this.setBs(hardware.getBs());
		this.setCpu(hardware.getCpu());
		this.setHostname(hardware.getHostname());
		this.setModel(hardware.getModel());
		this.setRam(hardware.getRam());
		this.setSn(hardware.getSn());
		this.setDate(DateUtil.formatDate(hardware.getDate() == null ? 0 :hardware.getDate()));
		this.setLizenz(hardware.getLizenz());
		this.setSoftware(hardware.getSoftware());
		this.setLastLogin(DateUtil.formatDate(hardware.getLastlogin()));
		this.setDescription(hardware.getDescription());
		this.setIp(hardware.getIp());
		this.setCategorie(hardware.getCategorie());
		this.setOwnerLocation(hardware.getOwnerlocation());
		this.setAktivLocation(hardware.getAktivlocation());
		this.setDepartment(hardware.getDepartment());
		this.setEncodingkey(hardware.getEncodingkey());
		this.setEncodingname(hardware.getEncodingname());
		if (hardware.getDate() != null)
			this.setDate(DateUtil.formatDate(hardware.getDate()));
		this.setVerliehen(hardware.getVerliehen());
		if (hardware.getVerliehenAn() != null)
			this.setVerliehenAn(hardware.getVerliehenAn().getUsername());
		if (hardware.getVerliehenBis() != null)
			this.setVerliehenBis(DateUtil.formatDate(hardware.getVerliehenBis()));

		if (hardware.getVerliehen()) {
			ILDAPService ldapService = BeanUtil.getBean(ILDAPService.class);
			ADUserData ldapUsername = ldapService.getLDAPUserByName(hardware.getVerliehenAn().getUsername());
			if (ldapUsername != null) {
				this.setVerliehenMobile(ldapUsername.getMobile());
				this.setVerliehenTel(ldapUsername.getTelephoneNumber());
			}
		}

	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(String lastLogin) {
		this.lastLogin = lastLogin;
	}

	public Long getHid() {
		return hid;
	}

	public void setHid(Long hid) {
		this.hid = hid;
	}

	public String getCategorie() {
		return categorie;
	}

	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}

	public int getOwnerLocation() {
		return ownerLocation;
	}

	public void setOwnerLocation(int ownerLocation) {
		this.ownerLocation = ownerLocation;
	}

	public int getAktivLocation() {
		return aktivLocation;
	}

	public void setAktivLocation(int aktivLocation) {
		this.aktivLocation = aktivLocation;
	}

	public ADUserData getInUseInformation() {
		return inUseInformation;
	}

	public void setInUseInformation(ADUserData inUseInformation) {
		this.inUseInformation = inUseInformation;
	}

	public ADUserData getOwnerInformation() {
		return ownerInformation;
	}

	public void setOwnerInformation(ADUserData ownerInformation) {
		this.ownerInformation = ownerInformation;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getEncodingname() {
		return encodingname;
	}

	public void setEncodingname(String encodingname) {
		this.encodingname = encodingname;
	}

	public String getEncodingkey() {
		return encodingkey;
	}

	public void setEncodingkey(String encodingkey) {
		this.encodingkey = encodingkey;
	}

}
