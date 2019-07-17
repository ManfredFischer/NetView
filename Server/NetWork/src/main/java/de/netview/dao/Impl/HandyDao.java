package de.netview.dao.Impl;

import java.util.List;

import org.springframework.stereotype.Component;

import de.netview.dao.IHandyDao;
import de.netview.dao.config.AbstractDao;
import de.netview.model.Handy;

@Component
public class HandyDao extends AbstractDao<Handy> implements IHandyDao {

	@Override
	public void addHandy(Handy handy) {
		getSession().save(handy);
	}

	@Override
	public void updateHandy(Handy handy) {
		getSession().update(handy);
	}

	@Override
	public void removeHandy(Long hid) {
		getSession().delete(hid);
	}

	@Override
	public List getHandy() {
		return getSession().createQuery("from Handy").list();
	}

	@Override
	public Handy getHandyById(Long hid) {
		// TODO Auto-generated method stub
		return null;
	}

}
