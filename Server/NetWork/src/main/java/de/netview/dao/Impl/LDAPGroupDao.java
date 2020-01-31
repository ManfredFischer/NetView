package de.netview.dao.Impl;

import de.netview.dao.ILDAPGroupDao;
import de.netview.dao.config.AbstractDao;
import de.netview.model.LDAPGroup;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LDAPGroupDao  extends AbstractDao implements ILDAPGroupDao {

    @Override
    public List getLDAPGroups(){
        return get("ldapgroup");
    }

    @Override
    public LDAPGroup getLDAPGroupByID(Long lpgid){
        return (LDAPGroup) getById(lpgid,this.getClass());
    }

    @Override
    public LDAPGroup addLdapGroup(LDAPGroup ldapGroup){
        return (LDAPGroup) save(ldapGroup);
    }

    @Override
    public void deleteLdapGroup(LDAPGroup ldapGroup){
        delete(ldapGroup);
    }
}
