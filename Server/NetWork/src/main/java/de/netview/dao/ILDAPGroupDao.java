package de.netview.dao;

import de.netview.model.LDAPGroup;

import java.util.List;

public interface ILDAPGroupDao {
    List getLDAPGroups();

    LDAPGroup getLDAPGroupByID(Long lpgid);

    LDAPGroup addLdapGroup(LDAPGroup ldapGroup);

    void deleteLdapGroup(LDAPGroup ldapGroup);
}
