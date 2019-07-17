package de.netview.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.netview.dao.IMobileUserDao;
import de.netview.model.MobileUser;
import de.netview.service.IMobileUserService;

@Service
public class MobileUserService implements IMobileUserService {
	
	@Autowired
	private IMobileUserDao mobileUserDao;

	@Override
	public MobileUser addMobileUser(MobileUser mobileUser) {
		mobileUserDao.addMobileUser(mobileUser);
		return mobileUser;
	}

	@Override
	public MobileUser updateMobileUser(MobileUser mobileUser) {
		mobileUserDao.updateMobileUser(mobileUser);
		return mobileUser;
	}

	@Override
	public void deleteMobileUser(Long muid) {
		mobileUserDao.deleteMobileUser(muid);
	}

	@Override
	public List getMobileUser() {
		return mobileUserDao.getMobileUser();
	}

	@Override
	public MobileUser getMobileUserByID(Long muid) {
		return mobileUserDao.getMobileUserByID(muid);
	}

}
