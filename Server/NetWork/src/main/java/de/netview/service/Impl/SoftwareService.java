package de.netview.service.Impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.netview.dao.ISoftwareDao;
import de.netview.model.Software;
import de.netview.service.ISoftwareService;

@Service
public class SoftwareService implements ISoftwareService {

	@Autowired
	private ISoftwareDao softwareDao;

	@Override
	@Transactional
	public void insertSoftware(Software software) {
		softwareDao.insertSoftware(software);
						
	}

}
