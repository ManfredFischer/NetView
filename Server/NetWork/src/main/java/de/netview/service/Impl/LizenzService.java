package de.netview.service.Impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.netview.dao.ILizenzDao;
import de.netview.dao.Impl.LizenzDao;
import de.netview.model.Lizenz;
import de.netview.service.ILizenzService;

@Service
@Transactional
public class LizenzService implements ILizenzService {
	
	@Autowired
	private ILizenzDao hardwareLizenzDao;

	@Override
	public void insertLizenz(Lizenz lizenz) {
		hardwareLizenzDao.insertLizenz(lizenz);		
	}

}
