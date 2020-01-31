package de.netview.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.*;

@Entity
@Table(name = "DEPARTMENT")
public class Department implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1089319279002474975L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false, precision = 10)
	private Long did;
	private String displayname;
	private String name;
	private String position;

	@ManyToMany(cascade=CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinTable(name = "department_ldapgroup", joinColumns = { @JoinColumn(name = "did", nullable = false) }, inverseJoinColumns = {
			@JoinColumn(name = "lgid", nullable = false) })
	private List<LDAPGroup> groups = new ArrayList();

	
	public Long getDid() {
		return did;
	}
	public void setDid(Long did) {
		this.did = did;
	}
	public String getDisplayname() {
		return displayname;
	}
	public void setDisplayname(String displayname) {
		this.displayname = displayname;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}

	public List<LDAPGroup> getGroups() {
		return groups;
	}

	public void setGroups(List<LDAPGroup> groups) {
		this.groups = groups;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Department that = (Department) o;
		return Objects.equals(did, that.did) &&
				Objects.equals(displayname, that.displayname) &&
				Objects.equals(name, that.name) &&
				Objects.equals(position, that.position);
	}

	@Override
	public int hashCode() {
		return Objects.hash(did, displayname, name, position);
	}
}
