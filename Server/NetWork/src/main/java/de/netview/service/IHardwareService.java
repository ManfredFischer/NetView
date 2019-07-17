package de.netview.service;

import java.util.List;
import java.util.Map;

import de.netview.data.HardwareData;
import de.netview.data.HardwareInformation;
import de.netview.model.Hardware;
import de.netview.model.Lizenz;

public interface IHardwareService {

	Hardware insertHardware(Hardware hardware);

	List<HardwareInformation> getAllHardware(String categorie);

	void loginHardware(String hostname, String username);

	void logoutHardware(String hostname);

	void deleteHardware(Long hid);

	void deleteHardwareLizenz(Long hid, Long lid);

	void addHardwareLizenz(Long hid, Long lid);

	Hardware getHardwareByHostname(String hostname);

	HardwareData getHardwareDataById(long hid);

	Hardware getHardwareByOwner(String owner);

	void saveHardware(Hardware hardware);

	List<Hardware> getAllHardware();

	List<Hardware> getHardwareByOwnerList(String owner);

	List<Hardware> getHardwareByUserList(String user);

	Hardware getHardwareById(long hid);

	void changeHardwareOwner(Map value);

}
