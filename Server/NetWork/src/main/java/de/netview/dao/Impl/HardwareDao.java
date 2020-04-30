package de.netview.dao.Impl;

import java.util.List;

import de.netview.data.AllInformation;
import de.netview.data.HardwareInformation;
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
		HardwareInformation hardwareInformation = new HardwareInformation(hardware);
		hardwareInformation.setRentDetails();
		if (hardware.getCategorie().equalsIgnoreCase("client")) {
			AllInformation.updateOrRemoveClients(hardwareInformation,false);
		} else {
			AllInformation.updateOrRemoveServer(hardwareInformation, false);
		}
	}

	@Override
	public Hardware getHardwareByName(String hostname) {
		return  (Hardware) getSession().createQuery("from Hardware where UPPER(hostname) = UPPER(:hostname)").setParameter("hostname", hostname).uniqueResult();
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
	public Hardware getHardwareByOwnerLastLogin(String user) {
		List hardwareList =  getSession().createQuery("from Hardware where UPPER(aktivusername) = UPPER(:user) ORDER BY lastlogin ASC").setParameter("user", user).list();
		if (hardwareList == null || hardwareList.isEmpty()){
			return null;
		}else{
			return (Hardware) hardwareList.get(hardwareList.size()-1);
		}

	}
	
	@Override
	public List<Hardware> getAllHardware() {
		return getSession().createQuery("from Hardware").list();
	}

	@Override
	public void deleteHardware(Hardware hardware) {

		if (hardware.getCategorie().equalsIgnoreCase("client")) {
			AllInformation.updateOrRemoveClients(new HardwareInformation(hardware),true);
		} else {
			AllInformation.updateOrRemoveServer(new HardwareInformation(hardware), true);
		}

		getSession().delete(hardware);

	}

	@Override
	public Hardware getHardwareById(Long hid) {
		return  (Hardware) getSession().createQuery("from Hardware where hid = :hid ").setParameter("hid", hid).uniqueResult();
	}
	
	@Override
	public List getHardwareByOwner(String owner) {
		return   getSession().createQuery("from Hardware where UPPER(owner) = UPPER(:owner)").setParameter("owner", owner).list();	
	}
	
	@Override
	public List getHardwareByUser(String user) {
		return   getSession().createQuery("from Hardware where UPPER(aktivusername) = UPPER(:user)").setParameter("user", user).list();	
	}

}
