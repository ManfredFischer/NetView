package de.netview.service.Impl;

import de.netview.dao.ILDAPGroupDao;
import de.netview.model.LDAPGroup;
import de.netview.service.ILDAPGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class LDAPGroupService implements ILDAPGroupService {

    @Autowired
    private ILDAPGroupDao ldapGroupDao;

    @Override
    @Transactional
    public List getLDAPGroups(){
        return ldapGroupDao.getLDAPGroups();
    }

    @Override
    @Transactional
    public LDAPGroup addLDAPGroups(LDAPGroup ldapGroup){
        return ldapGroupDao.addLdapGroup(ldapGroup);
    }

    @Override
    @Transactional
    public void deleteLDAPGroups(LDAPGroup ldapGroup){
        ldapGroupDao.deleteLdapGroup(ldapGroup);
    }
}
