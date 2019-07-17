package de.netview.dao.Impl;

import java.util.List;

import org.springframework.stereotype.Component;

import de.netview.dao.IMobileUserDao;
import de.netview.dao.config.AbstractDao;
import de.netview.model.MobileUser;

@Component
public class MobileUserDao extends AbstractDao<MobileUser> implements IMobileUserDao {

	@Override
	public void addMobileUser(MobileUser mobileUser) {
		getSession().save(mobileUser);
	}

	@Override
	public void updateMobileUser(MobileUser mobileUser) {
		getSession().update(mobileUser);
	}

	@Override
	public void deleteMobileUser(Long muid) {
		getSession().delete(muid);
	}

	@Override
	public List getMobileUser() {
		return getSession().createQuery("from Mobileuser").list();
	}

	@Override
	public MobileUser getMobileUserByID(Long muid) {
		return (MobileUser) getSession().createQuery("from Mobileuser where muid = :muid").setParameter("muid", muid).uniqueResult();
	}

}
