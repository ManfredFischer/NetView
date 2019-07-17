package de.netview.dao;

import java.util.List;

import de.netview.model.Hardware;

public interface IHardwareDAO {
	
	public void saveOrUpdateHardware(Hardware hardware);
	public Hardware getHardwareByName(String hostname);
	public Hardware getHardwareById(Long hid);
	public List<Hardware> getAllHardware(String categorie);
	public void deleteHardware(Hardware hardware);
	Hardware getHardwareById(long hid);
	List getHardwareByOwner(String owner);
	List<Hardware> getAllHardware();
	List getHardwareByUser(String user);

}
