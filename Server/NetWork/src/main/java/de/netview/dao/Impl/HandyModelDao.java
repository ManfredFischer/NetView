package de.netview.dao.Impl;

import java.util.List;

import org.springframework.stereotype.Component;

import de.netview.dao.IHandyModelDao;
import de.netview.dao.config.AbstractDao;
import de.netview.model.HandyModel;

@Component
public class HandyModelDao extends AbstractDao<HandyModel> implements IHandyModelDao {

	@Override
	public void addHandyModel(HandyModel handyModel) {
		getSession().save(handyModel);
	}

	@Override
	public void updateHandyModel(HandyModel handyModel) {
		getSession().update(handyModel);
	}

	@Override
	public void removeHandyModel(Long hmid) {
		getSession().delete(getHandyModelById(hmid));
	}

	@Override
	public List getHandyModel() {
		return getSession().createQuery("from HandyModel").list();
	}

	@Override
	public HandyModel getHandyModelById(Long hmid) {
		// TODO Auto-generated method stub
		return (HandyModel) getSession().createQuery("from HandyModel where hmid = :hmid").setParameter("hmid", hmid).uniqueResult();
	}

}
