package de.netview.dao.Impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import de.netview.dao.IHardwareDAO;
import de.netview.dao.config.AbstractDao;
import de.netview.model.Hardware;
import de.netview.model.Location;

@Repository
public class HardwareDao extends AbstractDao<Hardware> implements IHardwareDAO{

	@Override
	public void saveOrUpdateHardware(Hardware hardware) {
		getSession().saveOrUpdate(hardware);		
	}

	@Override
	public Hardware getHardwareByName(String hostname) {
		return  (Hardware) getSession().createQuery("from Hardware where hostname = :hostname").setParameter("hostname", hostname).uniqueResult();	
	}

	@Override
	public List<Hardware> getAllHardware() {
		return getSession().createQuery("from Hardware").list();
	}

}
