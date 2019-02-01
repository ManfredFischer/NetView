package de.netview.service;

import javax.naming.directory.Attributes;

import de.netview.data.ADUserData;
import de.netview.data.ADUserUpdateData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ILDAPService {

    List getUsers();
    void createNewUser(ADUserUpdateData adUserUpdateData);
    void updateUser(ADUserUpdateData adUserUpdateData) throws Exception;
    void updateUserPassword(String username, String password);
    Attributes getUserAttributes(String username);
    String getDepartementByName(String name);
	ADUserData getUserByName(String name);

}
