package de.netview.dao;

import de.netview.model.LDAPUser;

public interface ILDAPUserDao {
	
	public void addOrUpdateLDAPUser(LDAPUser ldapUser);
	public void deleteLDAPUser(Long luid);
	public LDAPUser getLDAPUserByName(String username);

}
