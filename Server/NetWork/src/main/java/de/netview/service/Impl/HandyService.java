package de.netview.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.netview.dao.IHandyDao;
import de.netview.model.Handy;
import de.netview.service.IHandyService;

@Service
public class HandyService implements IHandyService {
	
	@Autowired
	private IHandyDao handyDao;

	@Override
	public Handy addHandy(Handy handy) {
		handyDao.addHandy(handy);
		return handy;
	}

	@Override
	public Handy updateHandy(Handy handy) {
		handyDao.updateHandy(handy);
		return handy;
	}

	@Override
	public void removeHandy(Long hid) {
		handyDao.removeHandy(hid);
	}

	@Override
	public List getHandy() {
		return handyDao.getHandy();
	}

	@Override
	public Handy getHandyById(Long hid) {
		return handyDao.getHandyById(hid);
	}

}
