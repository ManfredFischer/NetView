package de.netview.service.Impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.netview.dao.Impl.SystemuserDao;
import de.netview.model.Systemuser;
import de.netview.service.ISystemUserService;

@Service
public class SystemuserService implements ISystemUserService {
	
	@Autowired
	private SystemuserDao userDao;

	@Override
	public void createUser(Systemuser systemuser) {
		userDao.save(systemuser);
	}


	@Override
	@Transactional
	public Systemuser getSystemuserByUsername(String username) {
		return userDao.getUserByUsername(username);
	}
}
