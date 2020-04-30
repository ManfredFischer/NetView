package de.netview.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "LDAPUSER")
public class LDAPUser {

	private Long luid;
	private String username;
	private String token;
	private String description;
	private String pin;
	private String puk;
	private List<Hardware> hardwarerent = new ArrayList<>();
	private List<Lizenz> lizenz = new ArrayList<>();

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false, precision = 10)
	public Long getLuid() {
		return luid;
	}

	public void setLuid(Long luid) {
		this.luid = luid;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public String getPuk() {
		return puk;
	}

	public void setPuk(String puk) {
		this.puk = puk;
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


	@ManyToMany(cascade = CascadeType.REFRESH)
	@JoinTable(name = "ldapuser_lizenz", joinColumns = { @JoinColumn(name = "luid") }, inverseJoinColumns = {
			@JoinColumn(name = "lid") })
	@JsonIgnore
	public List<Lizenz> getLizenz() {
		return lizenz;
	}
	
	public void setLizenz(List<Lizenz> lizenz) {
		this.lizenz = lizenz;
	}
	
	@OneToMany(cascade = CascadeType.REFRESH)
	@JoinTable(name = "ldapuser_hardware", joinColumns = { @JoinColumn(name = "luid") }, inverseJoinColumns = {
			@JoinColumn(name = "hid") })
	@JsonIgnore
	public List<Hardware> getHardwarerent() {
		return hardwarerent;
	}


	public void setHardwarerent(List<Hardware> hardwarerent) {
		this.hardwarerent = hardwarerent;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((hardwarerent == null) ? 0 : hardwarerent.hashCode());
		result = prime * result + ((lizenz == null) ? 0 : lizenz.hashCode());
		result = prime * result + ((luid == null) ? 0 : luid.hashCode());
		result = prime * result + ((token == null) ? 0 : token.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
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
		LDAPUser other = (LDAPUser) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (hardwarerent == null) {
			if (other.hardwarerent != null)
				return false;
		} else if (!hardwarerent.equals(other.hardwarerent))
			return false;
		if (lizenz == null) {
			if (other.lizenz != null)
				return false;
		} else if (!lizenz.equals(other.lizenz))
			return false;
		if (luid == null) {
			if (other.luid != null)
				return false;
		} else if (!luid.equals(other.luid))
			return false;
		if (token == null) {
			if (other.token != null)
				return false;
		} else if (!token.equals(other.token))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
}
