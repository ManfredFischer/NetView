package de.netview.data;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.netview.model.Document;
import de.netview.model.Hardware;

public class HardwareInformation implements Serializable {

	private static final long serialVersionUID = -344715559006434406L;
	private Long hid;
	private String hostname;
	private String ip;
	private String description;
	private String owner;
	private String aktivUsername;
	private String aktivUserPhone;
	private String aktivDate;
	private String lastLogin;
	private String model;
	private String bs;
	private String cpu;
	private String ram;
	private String sn;
	private String categorie;
	private String department;
	private String icon;
	private Boolean verliehen;
	private String verliehenAn;
	private String verliehenBis;
	private String encodingkey;
	private String encodingname;
	private int aktivlocation;
	private int ownerlocation;

	public int getAktivlocation() {
		return aktivlocation;
	}

	public void setAktivlocation(int aktivlocation) {
		this.aktivlocation = aktivlocation;
	}

	public int getOwnerlocation() {
		return ownerlocation;
	}

	public void setOwnerlocation(int ownerlocation) {
		this.ownerlocation = ownerlocation;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	private Integer status;

	public String getVerliehenBis() {
		return verliehenBis;
	}

	public void setVerliehenBis(String verliehenBis) {
		this.verliehenBis = verliehenBis;
	}

	public HardwareInformation(Hardware hardware) {
		this.hid = hardware.getHid();
		this.hostname = hardware.getHostname();
		this.ip = hardware.getIp();
		this.description = hardware.getDescription();
		this.status = hardware.getStatus();
		this.owner = hardware.getOwner();
		if (hardware.getAktivusername() != null) {
			String[] user = hardware.getAktivusername().split(".");
			if (user.length > 1) {
				this.aktivUsername = hardware.getAktivusername().split(".")[0] + " "
						+ hardware.getAktivusername().split(".")[1];
			} else {
				this.aktivUsername = hardware.getAktivusername();
			}
		}
		this.aktivDate = hardware.getAktivdate();
		this.lastLogin = hardware.getLastlogin();
		this.model = hardware.getModel();
		this.bs = hardware.getBs();
		this.cpu = hardware.getCpu();
		this.ram = hardware.getRam();
		this.sn = hardware.getSn();
		this.categorie = hardware.getCategorie();
		this.department = hardware.getDepartment();
		this.setIcon(hardware.getIcon());
		this.setAktivUserPhone(hardware.getAktivuserphone());
		this.setEncodingkey(hardware.getEncodingkey());
		this.setEncodingname(hardware.getEncodingname());
		this.setAktivlocation(hardware.getAktivlocation());
		this.setOwnerlocation(hardware.getOwnerlocation());
		
		if (hardware.getLDAPUser() != null && hardware.getLDAPUser().size() > 0) {
			this.setVerliehen(true);
			this.setVerliehenAn(hardware.getLDAPUser().get(0).getUsername());
			if (hardware.getVerliehenBis() != null) {
				String pattern = "dd.MM.yyyy";
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
				String date = simpleDateFormat.format(new Date(hardware.getVerliehenBis()));
				this.setVerliehenBis(date);
			}
		}else {
			this.setVerliehen(false);
		}

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

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getAktivUserPhone() {
		return aktivUserPhone;
	}

	public void setAktivUserPhone(String aktivUserPhone) {
		this.aktivUserPhone = aktivUserPhone;
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
