package de.netview.service;

import java.util.List;
import java.util.Map;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

import de.netview.data.ADUserData;
import de.netview.data.WizardUser;
import de.netview.data.LDAPUserData;
import de.netview.model.Hardware;
import de.netview.model.LDAPGroup;
import de.netview.model.LDAPUser;

public interface ILDAPService {

    Hardware checkHardwareByDomain(Hardware hardware);

    List getLDAPADUsers();

    List<LDAPGroup> getLDAPGroups();

    boolean tryConnectionToLDAP();

    void addUserToGroup(String userDn, String groupDn) throws NamingException;

    List<String> getLDAPUserGroups(String username);
    List<String> getLDAPUserGroupsWithPath(String username, Boolean withAllPath);

    void createLDAPNewUser(WizardUser wizardUser);
    void updateLDAPUser(WizardUser wizardUser) throws Exception;
    void updateLDAPUserPassword(String username, String password);
    Attributes getLDAPUserAttributes(String username);
	ADUserData getLDAPUserByName(String name);
	LDAPUserData getLDAPUserData(String username);
	void addOrUpdateLDAPUser(LDAPUser ldapUser);
	void rentHardwareForLDAPUser(Map value);
	void getBackHardwareFromLDAPUser(Map value);

}
