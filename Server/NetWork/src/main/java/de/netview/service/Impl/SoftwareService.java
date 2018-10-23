package de.netview.service.Impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.netview.dao.ISoftwareDao;
import de.netview.model.Software;
import de.netview.service.ISoftwareService;

@Service
@Transactional
public class SoftwareService implements ISoftwareService {

	@Autowired
	private ISoftwareDao softwareDao;

	@Override
	public Software insertSoftware(Software software) {
		Software softwareTemp = softwareDao.getSoftwareByName(software.getName());
		if (softwareTemp == null) {
			softwareDao.insertSoftware(software);
		}else {
			software = softwareTemp;
		}
		return software;				
	}

}
