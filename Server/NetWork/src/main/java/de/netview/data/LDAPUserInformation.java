package de.netview.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import de.netview.model.Hardware;
import de.netview.model.LDAPUser;
import de.netview.model.Lizenz;

public class LDAPUserInformation implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -320454294025384012L;
	private Long luid;
	private String username;
	private String token;
	private String description;
	private String phone;	
	private List<LizenzData> lizenz = new ArrayList<LizenzData>();
	private List<HardwareInformation> hardwarerent = new ArrayList<>();
	
	public LDAPUserInformation(LDAPUser ldapUser) {
		
		if (ldapUser != null) {
		 this.luid = ldapUser.getLuid();
		 this.username = ldapUser.getUsername();
		 this.token = ldapUser.getToken();
		 this.description = ldapUser.getDescription();
		 this.phone = ldapUser.getPhone();
		
		 for (Lizenz lizenz: ldapUser.getLizenz()) {
			this.lizenz.add(new LizenzData(lizenz));
		 }
		
		 for (Hardware hardwareData : ldapUser.getHardwarerent()) {
			this.hardwarerent.add(new HardwareInformation(hardwareData));
		 }
		}
		
	}

	public Long getLuid() {
		return luid;
	}

	public void setLuid(Long luid) {
		this.luid = luid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public List<LizenzData> getLizenz() {
		return lizenz;
	}

	public void setLizenz(List<LizenzData> lizenz) {
		this.lizenz = lizenz;
	}

	public List<HardwareInformation> getHardwarerent() {
		return hardwarerent;
	}

	public void setHardwarerent(List<HardwareInformation> hardwarerent) {
		this.hardwarerent = hardwarerent;
	}
	
	


}
