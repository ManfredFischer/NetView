package de.netview.model;

import java.io.Serializable;
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
public class Hardware implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 236138963642103844L;
	private Long hid;
	private String hostname;
	private String owner;
	private String department;
	private String ip;
	private String aktivusername;
	private String aktivuserphone;
	private String aktivdate;
	private String mac;
	private String storage;
	private String lastlogin;
	private String model;
	private String bs;
	private String cpu;
	private String ram;
	private String sn;
	private String description;
	private String categorie;
	private String encodingname;
	private String encodingkey;
	private Integer status;
	private int location;
	private String icon;
	private Boolean verliehen;
	private Long verliehenBis;
	private Long receivedate;
	private String bill;
	private String deliverynote;
	private String billnumber;
	private List<Lizenz> lizenz = new ArrayList<Lizenz>();
	private List<Software> software = new ArrayList<Software>();
	private List<LDAPUser> LDAPUser = new ArrayList<LDAPUser>();

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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((aktivdate == null) ? 0 : aktivdate.hashCode());
		result = prime * result + ((aktivusername == null) ? 0 : aktivusername.hashCode());
		result = prime * result + ((bs == null) ? 0 : bs.hashCode());
		result = prime * result + ((cpu == null) ? 0 : cpu.hashCode());
		result = prime * result + (int) (hid ^ (hid >>> 32));
		result = prime * result + ((hostname == null) ? 0 : hostname.hashCode());
		result = prime * result + ((lastlogin == null) ? 0 : lastlogin.hashCode());
		result = prime * result + ((lizenz == null) ? 0 : lizenz.hashCode());
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
		Hardware other = (Hardware) obj;
		if (aktivdate == null) {
			if (other.aktivdate != null)
				return false;
		} else if (!aktivdate.equals(other.aktivdate))
			return false;
		if (aktivusername == null) {
			if (other.aktivusername != null)
				return false;
		} else if (!aktivusername.equals(other.aktivusername))
			return false;
		if (bs == null) {
			if (other.bs != null)
				return false;
		} else if (!bs.equals(other.bs))
			return false;
		if (cpu == null) {
			if (other.cpu != null)
				return false;
		} else if (!cpu.equals(other.cpu))
			return false;
		if (hid != other.hid)
			return false;
		if (hostname == null) {
			if (other.hostname != null)
				return false;
		} else if (!hostname.equals(other.hostname))
			return false;
		if (lastlogin == null) {
			if (other.lastlogin != null)
				return false;
		} else if (!lastlogin.equals(other.lastlogin))
			return false;
		if (lizenz == null) {
			if (other.lizenz != null)
				return false;
		} else if (!lizenz.equals(other.lizenz))
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

	@Override
	public String toString() {
		return "Hardware [hid=" + hid + ", hostname=" + hostname + ", owner=" + owner + ", aktivusername="
				+ aktivusername + ", aktivdate=" + aktivdate + ", lastlogin=" + lastlogin + ", model=" + model + ", bs="
				+ bs + ", cpu=" + cpu + ", ram=" + ram + ", sn=" + sn + ", lizenz=" + lizenz + "]";
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

	public int getLocation() {
		return location;
	}

	public void setLocation(int location) {
		this.location = location;
	}
	
	@JsonIgnore
	public void wrappeValues(Hardware hardware) {
		this.aktivdate = hardware.getAktivdate();
		this.aktivusername = hardware.getAktivusername();
		this.bs = hardware.getBs();
		this.categorie = hardware.getCategorie();
		this.cpu = hardware.getCpu();
		this.description = hardware.getDescription();
		this.owner = hardware.getOwner();
		this.hostname = hardware.getHostname();
		this.ip = hardware.getIp();
		this.lastlogin = hardware.getLastlogin();
		this.location = hardware.getLocation();
		this.model = hardware.getModel();
		this.ram = hardware.getRam();
		this.sn = hardware.getSn();
		this.department = hardware.department;
		this.icon = hardware.icon;
		this.aktivuserphone = hardware.aktivuserphone;
		this.LDAPUser = hardware.LDAPUser;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getAktivuserphone() {
		return aktivuserphone;
	}

	public void setAktivuserphone(String aktivuserphone) {
		this.aktivuserphone = aktivuserphone;
	}


	public Boolean getVerliehen() {
		return verliehen;
	}


	public void setVerliehen(Boolean verliehen) {
		this.verliehen = verliehen;
	}

	@ManyToMany(mappedBy = "hardwarerent")
	public List<LDAPUser> getLDAPUser() {
		return LDAPUser;
	}


	public void setLDAPUser(List<LDAPUser> lDAPUser) {
		LDAPUser = lDAPUser;
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

	public Long getReceivedate() {
		return receivedate;
	}

	public void setReceivedate(Long receivedate) {
		this.receivedate = receivedate;
	}

	public String getBill() {
		return bill;
	}

	public void setBill(String bill) {
		this.bill = bill;
	}

	public String getDeliverynote() {
		return deliverynote;
	}

	public void setDeliverynote(String deliverynote) {
		this.deliverynote = deliverynote;
	}

	public String getBillnumber() {
		return billnumber;
	}

	public void setBillnumber(String billnumber) {
		this.billnumber = billnumber;
	}
}
