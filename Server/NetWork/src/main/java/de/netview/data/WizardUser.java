package de.netview.data;

import java.io.Serializable;
import java.util.Map;

public class WizardUser implements Serializable {
	private String duplicateFrom;
	private WizardUserData user;
	private Map permission;


	public WizardUserData getUser() {
		return user;
	}

	public void setUser(WizardUserData user) {
		this.user = user;
	}

	public Map getPermission() {
		return permission;
	}

	public void setPermission(Map permission) {
		this.permission = permission;
	}




	public String getDuplicateFrom() {
		return duplicateFrom;
	}

	public void setDuplicateFrom(String duplicateFrom) {
		this.duplicateFrom = duplicateFrom;
	}
}
