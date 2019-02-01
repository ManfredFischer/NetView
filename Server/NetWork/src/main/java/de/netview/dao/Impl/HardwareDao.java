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
	public Hardware getHardwareById(long hid) {
		return  (Hardware) getSession().createQuery("from Hardware where hid = :hid").setParameter("hid", hid).uniqueResult();	
	}

	@Override
	public List<Hardware> getAllHardware(String categorie) {
		if (categorie.equalsIgnoreCase("clients")) {
			return getSession().createQuery("from Hardware where categorie = 'Client'").list();
		}
		return getSession().createQuery("from Hardware where categorie != 'Client'").list();
	}

	@Override
	public void deleteHardware(Hardware hardware) {
		getSession().delete(hardware);
	}

	@Override
	public Hardware getHardwareById(Long hid) {
		return  (Hardware) getSession().createQuery("from Hardware where hid = :hid").setParameter("hid", hid).uniqueResult();	
	}
	
	@Override
	public List getHardwareByOwner(String owner) {
		return   getSession().createQuery("from Hardware where owner = :owner").setParameter("owner", owner).list();	
	}

}
