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
	private String aktivusername;
	private String aktivdate;
	private String lastlogin;
	private String model;
	private String bs;
	private String cpu;
	private String ram;
	private String sn;
	private List<Lizenz> lizenz = new ArrayList<Lizenz>();
	private List<Software> software = new ArrayList<Software>();

	public void setSoftware(List<Software> software) {
		this.software = software;
	}

	@ManyToMany(cascade=CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "hardware_software", joinColumns = { @JoinColumn(name = "hid", nullable = false) }, inverseJoinColumns = {
			@JoinColumn(name = "sid", nullable = false) })
	public List<Software> getSoftware() {
		return software;
	}

	@ManyToMany(cascade=CascadeType.ALL)
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

}
