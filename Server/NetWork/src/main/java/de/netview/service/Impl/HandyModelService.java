package de.netview.service.Impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.netview.dao.IHandyModelDao;
import de.netview.model.HandyModel;
import de.netview.service.IHandyModelService;

@Service
public class HandyModelService implements IHandyModelService {

	@Autowired
	private IHandyModelDao handyModelDao;

	@Override
	@Transactional
	public HandyModel addHandyModel(HandyModel handyModel) {
		handyModelDao.addHandyModel(handyModel);
		return handyModel;
	}

	@Override
	@Transactional
	public HandyModel updateHandyModel(HandyModel handyModel) {
		handyModelDao.updateHandyModel(handyModel);
		return handyModel;
	}

	@Override
	@Transactional
	public void removeHandyModel(Long hmid) {
		handyModelDao.removeHandyModel(hmid);
	}

	@Override
	@Transactional
	public List getHandyModel() {
		return handyModelDao.getHandyModel();
	}

	@Override
	@Transactional
	public HandyModel getHandyModelById(Long hmid) {
		return handyModelDao.getHandyModelById(hmid);
	}

}
