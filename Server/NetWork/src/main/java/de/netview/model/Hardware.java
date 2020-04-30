package de.netview.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.netview.data.enums.HardwareStatus;

@Entity
@Table(name = "HARDWARE")
public class Hardware implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 236138963642103844L;
	private Long hid;
	private String hostname;
	private String owner;
	private String ownerphone;
	private String department;
	private String ip;
	private String aktivusername;
	private String aktivuserphone;
	private Long date;
	private String mac;
	private String storage;
	private Long lastlogin;
	private String model;
	private String bs;
	private String cpu;
	private String ram;
	private String sn;
	private String description;
	private String categorie;
	private String encodingname;
	private String encodingkey;
	private HardwareStatus status;
	private int ownerlocation;
	private int aktivlocation;
	private Boolean verliehen;
	private Long verliehenBis;
	private LDAPUser verliehenAn;

	private List<Lizenz> lizenz = new ArrayList<Lizenz>();
	private List<Software> software = new ArrayList<Software>();

	public String getOwnerphone() {
		return ownerphone;
	}

	public void setOwnerphone(String ownerphone) {
		this.ownerphone = ownerphone;
	}

	public void setSoftware(List<Software> software) {
		this.software = software;
	}

	@ManyToMany(cascade=CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinTable(name = "hardware_software", joinColumns = { @JoinColumn(name = "hid", nullable = false) }, inverseJoinColumns = {
			@JoinColumn(name = "sid", nullable = false) })
	public List<Software> getSoftware() {
		return software;
	}

	@ManyToMany(cascade=CascadeType.REFRESH)
	@JoinTable(name = "hardware_lizenz", joinColumns = { @JoinColumn(name = "hid") }, inverseJoinColumns = {
			@JoinColumn(name = "lid") })
	public List<Lizenz> getLizenz() {
		return lizenz;
	}

	public void setLizenz(List<Lizenz> lizenz) {
		this.lizenz = lizenz;
	}

	@Column(unique = true, name = "hostname", nullable = false)
	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public HardwareStatus getStatus() {
		return status;
	}

	public void setStatus(HardwareStatus status) {
		this.status = status;
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
	public Long getHid() {
		return hid;
	}

	public void setHid(Long hid) {
		this.hid = hid;
	}

	public Long getDate() {
		return date;
	}

	public void setDate(Long date) {
		this.date = date;
	}

	public String getAktivusername() {
		return aktivusername;
	}

	public void setAktivusername(String aktivusername) {
		this.aktivusername = aktivusername;
	}

	public Long getLastlogin() {
		return lastlogin;
	}

	public void setLastlogin(Long lastlogin) {
		this.lastlogin = lastlogin;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getCategorie() {
		return categorie;
	}

	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}

	public int getOwnerlocation() {
		return ownerlocation;
	}

	public void setOwnerlocation(int ownerlocation) {
		this.ownerlocation = ownerlocation;
	}

	public int getAktivlocation() {
		return aktivlocation;
	}

	public void setAktivlocation(int aktivlocation) {
		this.aktivlocation = aktivlocation;
	}

	@JsonIgnore
	public void wrappeValues(Hardware hardware) {
		this.date = hardware.getDate();
		this.aktivusername = hardware.getAktivusername();
		this.bs = hardware.getBs();
		this.categorie = hardware.getCategorie();
		this.cpu = hardware.getCpu();
		this.description = hardware.getDescription();
		this.owner = hardware.getOwner();
		this.hostname = hardware.getHostname();
		this.ip = hardware.getIp();
		this.lastlogin = hardware.getLastlogin();
		this.ownerlocation = hardware.getOwnerlocation();
		this.aktivlocation = hardware.getAktivlocation();
		this.model = hardware.getModel();
		this.ram = hardware.getRam();
		this.sn = hardware.getSn();
		this.department = hardware.department;
		this.aktivuserphone = hardware.aktivuserphone;
		this.verliehenAn = hardware.verliehenAn;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getAktivuserphone() {
		return aktivuserphone;
	}

	public void setAktivuserphone(String aktivuserphone) {
		this.aktivuserphone = aktivuserphone;
	}


	public Boolean getVerliehen() {
		return verliehen == null ? false : verliehen;
	}


	public void setVerliehen(Boolean verliehen) {
		this.verliehen = verliehen;
	}

	@ManyToOne
	@JoinTable(name = "ldapuser_hardware", joinColumns = { @JoinColumn(name = "luid") }, inverseJoinColumns = {
			@JoinColumn(name = "hid") })
	public LDAPUser getVerliehenAn() {
		return verliehenAn;
	}


	public void setVerliehenAn(LDAPUser lDAPUser) {
		verliehenAn = lDAPUser;
	}

	public Long getVerliehenBis() {
		return verliehenBis;
	}

	public void setVerliehenBis(Long verliehenBis) {
		this.verliehenBis = verliehenBis;
	}

	public String getStorage() {
		return storage;
	}

	public void setStorage(String storage) {
		this.storage = storage;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getEncodingkey() {
		return encodingkey;
	}

	public void setEncodingkey(String encodingkey) {
		this.encodingkey = encodingkey;
	}

	public String getEncodingname() {
		return encodingname;
	}

	public void setEncodingname(String encodingname) {
		this.encodingname = encodingname;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Hardware hardware = (Hardware) o;
		return ownerlocation == hardware.ownerlocation &&
				aktivlocation == hardware.aktivlocation &&
				Objects.equals(hid, hardware.hid) &&
				Objects.equals(hostname, hardware.hostname) &&
				Objects.equals(owner, hardware.owner) &&
				Objects.equals(department, hardware.department) &&
				Objects.equals(ip, hardware.ip) &&
				Objects.equals(aktivusername, hardware.aktivusername) &&
				Objects.equals(aktivuserphone, hardware.aktivuserphone) &&
				Objects.equals(date, hardware.date) &&
				Objects.equals(mac, hardware.mac) &&
				Objects.equals(storage, hardware.storage) &&
				Objects.equals(lastlogin, hardware.lastlogin) &&
				Objects.equals(model, hardware.model) &&
				Objects.equals(bs, hardware.bs) &&
				Objects.equals(cpu, hardware.cpu) &&
				Objects.equals(ram, hardware.ram) &&
				Objects.equals(sn, hardware.sn) &&
				Objects.equals(description, hardware.description) &&
				Objects.equals(categorie, hardware.categorie) &&
				Objects.equals(encodingname, hardware.encodingname) &&
				Objects.equals(encodingkey, hardware.encodingkey) &&
				status == hardware.status &&
				Objects.equals(verliehen, hardware.verliehen) &&
				Objects.equals(verliehenBis, hardware.verliehenBis) &&
				Objects.equals(lizenz, hardware.lizenz) &&
				Objects.equals(software, hardware.software) &&
				Objects.equals(verliehenAn, hardware.verliehenAn);
	}

	@Override
	public int hashCode() {
		return Objects.hash(hid, hostname, owner, department, ip, aktivusername, aktivuserphone, date, mac, storage, lastlogin, model, bs, cpu, ram, sn, description, categorie, encodingname, encodingkey, status, ownerlocation, aktivlocation, verliehen, verliehenBis, lizenz, software, verliehenAn);
	}
}
