package de.netview.service;

import java.util.List;
import java.util.Map;

import de.netview.data.HardwareData;
import de.netview.data.HardwareInformation;
import de.netview.model.Hardware;

import javax.transaction.Transactional;

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

    @Transactional
    abstract Hardware getHardwareByOwner(String owner);

	void saveHardware(Hardware hardware);

    @Transactional
    String getHostnameByOwnerLastLogin(String owner);

    List<Hardware> getAllHardware();

	List<Hardware> getHardwareByOwnerList(String owner);

	List<Hardware> getHardwareByUserList(String user);

	Hardware getHardwareById(long hid);

	void changeHardwareOwner(Map value);

	Hardware archivHardware(long hid);

}
