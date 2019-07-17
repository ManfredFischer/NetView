package de.netview.service;

import java.util.List;
import java.util.Map;

import javax.naming.directory.Attributes;

import de.netview.data.ADUserData;
import de.netview.data.ADUserUpdateData;
import de.netview.data.LDAPUserData;
import de.netview.model.LDAPUser;
import de.netview.model.Systemuser;

public interface ILDAPService {

    List getLDAPADUsers();
    void createLDAPNewUser(ADUserUpdateData adUserUpdateData);
    void updateLDAPUser(ADUserUpdateData adUserUpdateData) throws Exception;
    void updateLDAPUserPassword(String username, String password);
    Attributes getLDAPUserAttributes(String username);
	ADUserData getLDAPUserByName(String name);
	LDAPUserData getLDAPUserData(String username);
	void addOrUpdateLDAPUser(LDAPUser ldapUser);
	void rentHardwareForLDAPUser(Map value);
	void getBackHardwareFromLDAPUser(Map value);

}
