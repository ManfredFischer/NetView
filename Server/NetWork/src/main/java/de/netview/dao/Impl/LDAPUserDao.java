package de.netview.dao.Impl;

import org.springframework.stereotype.Repository;

import de.netview.dao.ILDAPUserDao;
import de.netview.dao.config.AbstractDao;
import de.netview.model.LDAPUser;

@Repository
public class LDAPUserDao extends AbstractDao<LDAPUser> implements ILDAPUserDao {

	@Override
	public void addOrUpdateLDAPUser(LDAPUser ldapUser) {
		getSession().saveOrUpdate(ldapUser);
	}

	@Override
	public void deleteLDAPUser(Long luid) {
		getSession().delete(luid);
	}

	@Override
	public LDAPUser getLDAPUserByName(String username) {
		return (LDAPUser) getSession().createQuery("from LDAPUser where UPPER(username) = UPPER(:username)").setParameter("username", username).uniqueResult();
	}

}
