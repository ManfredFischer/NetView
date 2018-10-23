package de.netview.dao;

import java.util.List;

import de.netview.model.Hardware;

public interface IHardwareDAO {
	
	public void saveOrUpdateHardware(Hardware hardware);
	public Hardware getHardwareByName(String hostname);
	public List<Hardware> getAllHardware();

}
