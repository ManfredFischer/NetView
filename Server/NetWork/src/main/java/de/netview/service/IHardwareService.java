package de.netview.service;

import java.util.List;
import java.util.Map;

import de.netview.data.HardwareData;
import de.netview.data.HardwareInformation;
import de.netview.model.Hardware;
import de.netview.model.Lizenz;

public interface IHardwareService {

	public Hardware insertHardware(Hardware hardware);
	public List<HardwareInformation> getAllHardware(String categorie);
	public void loginHardware(String hostname, String username);
	public void logoutHardware(String hostname );
	public void deleteHardware(Long hid);
	public void deleteHardwareLizenz(Long hid, Long lid);
	public void addHardwareLizenz(Long hid, Long lid);
	public Hardware getHardwareByHostname(String hostname);
	HardwareData getHardwareById(long hid);
	public Hardware getHardwareByOwner(String owner);
	
}
