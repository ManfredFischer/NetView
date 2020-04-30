package de.netview.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.netview.config.BeanUtil;
import de.netview.data.enums.HardwareStatus;
import de.netview.function.impl.DateUtil;
import de.netview.model.Hardware;
import de.netview.model.LDAPUser;
import de.netview.service.ILDAPService;
import de.netview.service.Impl.LDAPService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Objects;

public class HardwareInformation implements Serializable {

	private static final long serialVersionUID = -344715559006434406L;
	private Long hid;
	private String hostname;
	private String ip;
	private String description;
	private String owner;
	private String ownerPhone;
	private String ownerMobile;
	private String aktivUsername;
	private String aktivUserPhone;
	private String aktivUserMobile;
	private Long date;
	private Long lastLogin;
	private String model;
	private String bs;
	private String cpu;
	private String ram;
	private String sn;
	private String categorie;
	private int ownerLocation;
	private int aktivLocation;
	private String department;
	private Boolean verliehen;
	private String verliehenAn;
	private String verliehenBis;
	private String verliehenTel;
	private String verliehenMobile;
	private String encodingkey;
	private String encodingname;
	private HardwareStatus status;

	public String getOwnerMobile() {
		return ownerMobile;
	}

	public void setOwnerMobile(String ownerMobile) {
		this.ownerMobile = ownerMobile;
	}

	public String getAktivUserMobile() {
		return aktivUserMobile;
	}

	public void setAktivUserMobile(String aktivUserMobile) {
		this.aktivUserMobile = aktivUserMobile;
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

	public String getOwnerPhone() {
		return ownerPhone;
	}

	public void setOwnerPhone(String ownerPhone) {
		this.ownerPhone = ownerPhone;
	}

	public HardwareStatus getStatus() {
		return status;
	}

	public void setStatus(HardwareStatus status) {
		this.status = status;
	}

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
		this.date = hardware.getDate();
		this.lastLogin = hardware.getLastlogin();
		this.model = hardware.getModel();
		this.bs = hardware.getBs();
		this.cpu = hardware.getCpu();
		this.ram = hardware.getRam();
		this.sn = hardware.getSn();
		this.categorie = hardware.getCategorie();
		this.department = hardware.getDepartment();
		this.setOwnerLocation(hardware.getOwnerlocation());
		this.setAktivLocation(hardware.getAktivlocation());
		this.setAktivUserPhone(hardware.getAktivuserphone());
		this.setEncodingkey(hardware.getEncodingkey());
		this.setEncodingname(hardware.getEncodingname());
		this.setOwnerPhone(hardware.getOwnerphone());
		this.setVerliehen(hardware.getVerliehen());
		if (hardware.getVerliehenAn() != null)
			this.setVerliehenAn(hardware.getVerliehenAn().getUsername());
		if (hardware.getVerliehenBis() != null)
			this.setVerliehenBis(DateUtil.formatDate(hardware.getVerliehenBis()));
	}

	public void setRentDetails(){
		if (this.getVerliehen()) {
			ILDAPService ldapService = BeanUtil.getBean(ILDAPService.class);
			ADUserData ldapUsername = ldapService.getLDAPUserByName(this.getVerliehenAn());
			if (ldapUsername != null) {
				this.setVerliehenMobile(ldapUsername.getMobile());
				this.setVerliehenTel(ldapUsername.getTelephoneNumber());
			}
		}
	}

	public String getVerliehenAn() {
		return verliehenAn;
	}

	public void setVerliehenAn(String verliehenAn) {
		this.verliehenAn = verliehenAn;
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

	public Long getDate() {
		return date;
	}

	public void setDate(Long date) {
		this.date = date;
	}

	public Long getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Long lastLogin) {
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

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
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

	public String getAktivUserPhone() {
		return aktivUserPhone;
	}

	public void setAktivUserPhone(String aktivUserPhone) {
		this.aktivUserPhone = aktivUserPhone;
	}

	public Boolean getVerliehen() {
		return verliehen == null ? false : verliehen;
	}

	public void setVerliehen(Boolean verliehen) {
		this.verliehen = verliehen;
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		HardwareInformation that = (HardwareInformation) o;
		return ownerLocation == that.ownerLocation &&
				aktivLocation == that.aktivLocation &&
				Objects.equals(hid, that.hid) &&
				Objects.equals(hostname, that.hostname) &&
				Objects.equals(ip, that.ip) &&
				Objects.equals(description, that.description) &&
				Objects.equals(owner, that.owner) &&
				Objects.equals(aktivUsername, that.aktivUsername) &&
				Objects.equals(aktivUserPhone, that.aktivUserPhone) &&
				Objects.equals(date, that.date) &&
				Objects.equals(lastLogin, that.lastLogin) &&
				Objects.equals(model, that.model) &&
				Objects.equals(bs, that.bs) &&
				Objects.equals(cpu, that.cpu) &&
				Objects.equals(ram, that.ram) &&
				Objects.equals(sn, that.sn) &&
				Objects.equals(categorie, that.categorie) &&
				Objects.equals(department, that.department) &&
				Objects.equals(verliehen, that.verliehen) &&
				Objects.equals(verliehenAn, that.verliehenAn) &&
				Objects.equals(verliehenBis, that.verliehenBis) &&
				Objects.equals(encodingkey, that.encodingkey) &&
				Objects.equals(encodingname, that.encodingname) &&
				status == that.status;
	}

	@Override
	public int hashCode() {
		return Objects.hash(hid, hostname, ip, description, owner, aktivUsername, aktivUserPhone, date, lastLogin, model, bs, cpu, ram, sn, categorie, ownerLocation, aktivLocation, department, verliehen, verliehenAn, verliehenBis, encodingkey, encodingname, status);
	}
}
