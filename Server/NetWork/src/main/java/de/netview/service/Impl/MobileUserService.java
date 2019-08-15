package de.netview.service.Impl;

import java.util.ArrayList;
import java.util.List;

import de.netview.data.MobileUserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.netview.dao.IMobileUserDao;
import de.netview.model.MobileUser;
import de.netview.service.IMobileUserService;

import javax.transaction.Transactional;

@Service
public class MobileUserService implements IMobileUserService {
	
	@Autowired
	private IMobileUserDao mobileUserDao;

	@Override
	@Transactional
	public MobileUser addMobileUser(MobileUser mobileUser) {
		mobileUserDao.addMobileUser(mobileUser);
		return mobileUser;
	}

	@Override
	@Transactional
	public MobileUser updateMobileUser(MobileUser mobileUser) {
		mobileUserDao.updateMobileUser(mobileUser);
		return mobileUser;
	}

	@Override
	@Transactional
	public void deleteMobileUser(Long muid) {
		mobileUserDao.deleteMobileUser(muid);
	}

	@Override
	@Transactional
	public List getMobileUser() {
		List<MobileUser> mobileUserList = mobileUserDao.getMobileUser();
		ArrayList<MobileUserData> mobileUserData = new ArrayList<>();

		for (MobileUser mobileUser : mobileUserList){
			mobileUserData.add(new MobileUserData(mobileUser));
		}

		return mobileUserData;
	}

	@Override
	@Transactional
	public MobileUser getMobileUserByID(Long muid) {
		return mobileUserDao.getMobileUserByID(muid);
	}

}
