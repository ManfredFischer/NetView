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
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "LIZENZ")
public class Lizenz implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8904030561309253406L;
	private long lid;
	private String name;
	private String key;
	private int state;
	private String reserved;
	private String categorie;
	
	private List<Hardware> hardware = new ArrayList<>();
	private List<LDAPUser> LDAPUser = new ArrayList<>();
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false, precision = 10)
	public Long getLid() {
		return lid;
	}

	public void setLid(long lid) {
		this.lid = lid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
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
		result = prime * result + ((categorie == null) ? 0 : categorie.hashCode());
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		result = prime * result + (int) (lid ^ (lid >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + (int) (state ^ (state >>> 32));
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
		Lizenz other = (Lizenz) obj;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@ManyToMany(mappedBy = "lizenz")
	public List<Hardware> getHardware() {
		return hardware;
	}

	public void setHardware(List<Hardware> hardware) {
		this.hardware = hardware;
	}

	public String getReserved() {
		return reserved;
	}

	public void setReserved(String reserved) {
		this.reserved = reserved;
	}

	@ManyToMany(mappedBy = "lizenz")
	public List<LDAPUser> getLDAPUser() {
		return LDAPUser;
	}

	public void setLDAPUser(List<LDAPUser> lDAPUser) {
		LDAPUser = lDAPUser;
	}

	

	
	
	
	
	

}
