package de.netview.service;

import de.netview.model.LDAPGroup;

import javax.transaction.Transactional;
import java.util.List;

public interface ILDAPGroupService {


    @Transactional
    List getLDAPGroups();

    @Transactional
    LDAPGroup addLDAPGroups(LDAPGroup ldapGroup);

    @Transactional
    void deleteLDAPGroups(LDAPGroup ldapGroup);
}
