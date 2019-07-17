package de.netview.data;

import java.io.Serializable;
import java.util.List;

import de.netview.model.LDAPUser;

public class LDAPUserData implements Serializable {
	
	private static final long serialVersionUID = 2635072207562734288L;
	private ADUserData userData;
	private List aktivHardware;
	private List ownerHardware;
	private List lizenz;
	private LDAPUserInformation ldapUser;
		
	public ADUserData getUserData() {
		return userData;
	}
	public void setUserData(ADUserData userData) {
		this.userData = userData;
	}
	public List getAktivHardware() {
		return aktivHardware;
	}
	public void setAktivHardware(List aktivHardware) {
		this.aktivHardware = aktivHardware;
	}
	public List getOwnerHardware() {
		return ownerHardware;
	}
	public void setOwnerHardware(List ownerHardware) {
		this.ownerHardware = ownerHardware;
	}
	public List getLizenz() {
		return lizenz;
	}
	public void setLizenz(List lizenz) {
		this.lizenz = lizenz;
	}
	public LDAPUserInformation getLdapUser() {
		return ldapUser;
	}
	public void setLdapUser(LDAPUserInformation ldapUser) {
		this.ldapUser = ldapUser;
	}

}
