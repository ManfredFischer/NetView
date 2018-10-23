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

	private long lid;
	private String lizenz;
	private String lizenzkey;
	private String message;
	private List<Hardware> hardware = new ArrayList<Hardware>();

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "lizenz")
	@JsonIgnore
	public List<Hardware> getHardware() {
		return hardware;
	}

	@JsonIgnore
	public void setHardware(List<Hardware> hardware) {
		this.hardware = hardware;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false, precision = 10)
	public Long getLid() {
		return lid;
	}

	public void setLid(long lid) {
		this.lid = lid;
	}

	public String getLizenz() {
		return lizenz;
	}

	public void setLizenz(String lizenz) {
		this.lizenz = lizenz;
	}

	public String getLizenzkey() {
		return lizenzkey;
	}

	public void setLizenzkey(String lizenzkey) {
		this.lizenzkey = lizenzkey;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((lizenz == null) ? 0 : lizenz.hashCode());
		result = prime * result + ((lizenzkey == null) ? 0 : lizenzkey.hashCode());
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
		if (lizenz == null) {
			if (other.lizenz != null)
				return false;
		} else if (!lizenz.equals(other.lizenz))
			return false;
		if (lizenzkey == null) {
			if (other.lizenzkey != null)
				return false;
		} else if (!lizenzkey.equals(other.lizenzkey))
			return false;
		return true;
	}
	
	
	
	

}
