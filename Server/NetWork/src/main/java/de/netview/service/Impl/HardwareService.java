package de.netview.service.Impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.netview.dao.IHardwareDAO;
import de.netview.dao.ILizenzDao;
import de.netview.dao.ISoftwareDao;
import de.netview.data.HardwareData;
import de.netview.data.HardwareInput;
import de.netview.model.Hardware;
import de.netview.model.Lizenz;
import de.netview.model.Software;
import de.netview.service.IHardwareService;
import de.netview.service.ILizenzService;
import de.netview.service.ISoftwareService;

@Service
public class HardwareService implements IHardwareService {

	@Autowired
	private IHardwareDAO hardwareDao;

	@Autowired
	private ILizenzDao lizenzDao;
	
	@Autowired
	private ILizenzService lizenzService;

	@Autowired
	private ISoftwareDao softwareDao;

	@Transactional
	@Override
	public Hardware insertHardware(Hardware hardware) {

		Hardware hardwareInfo = hardwareDao.getHardwareByName(hardware.getHostname());

		if (hardwareInfo != null) {

			for (int i = 0; i < hardware.getSoftware().size(); i++) {
				boolean insert = true;
				for (int a = 0; a < hardwareInfo.getSoftware().size(); a++) {
					if (hardwareInfo.getSoftware().get(a).getName().equals(hardware.getSoftware().get(i).getName())) {
						insert = false;
						break;
					}
				}

				if (insert) {
					Software softwareTemp = softwareDao.getSoftwareByName(hardware.getSoftware().get(i).getName());
					if (softwareTemp == null) {
						softwareDao.insertSoftware(hardware.getSoftware().get(i));
						hardwareInfo.getSoftware().add(hardware.getSoftware().get(i));
					} else {
						hardwareInfo.getSoftware().add(softwareTemp);
					}
				}
			}

			for (int i = 0; i < hardware.getLizenz().size(); i++) {
				boolean insert = true;
				for (int a = 0; a < hardwareInfo.getLizenz().size(); a++) {
					if (hardwareInfo.getLizenz().get(a).equals(hardware.getLizenz().get(i).getKey())) {
						insert = false;
						break;
					}
				}

				if (insert) {
					Lizenz lizenzTemp = lizenzDao.getLizenzByName(hardware.getLizenz().get(i).getName(),
							hardware.getLizenz().get(i).getKey());
					if (lizenzTemp == null) {
						lizenzDao.insertLizenz(hardware.getLizenz().get(i));
						hardwareInfo.getLizenz().add(hardware.getLizenz().get(i));
					} else {
						lizenzService.checkLizenz(lizenzTemp);
						hardwareInfo.getLizenz().add(lizenzTemp);
					}
				}
			}

			hardwareDao.saveOrUpdateHardware(hardwareInfo);
			return hardwareInfo;

		} else {
			
			hardware.setHid(null);

			for (int i = 0; i < hardware.getSoftware().size(); i++) {
				Software softwareTemp = softwareDao.getSoftwareByName(hardware.getSoftware().get(i).getName());
				if (softwareTemp == null) {
					softwareDao.insertSoftware(hardware.getSoftware().get(i));
				} else {
					hardware.getSoftware().remove(i);
					hardware.getSoftware().add(softwareTemp);
				}
			}

			for (int i = 0; i < hardware.getLizenz().size(); i++) {
				Lizenz lizenzTemp = lizenzDao.getLizenzByName(hardware.getLizenz().get(i).getName(),
						hardware.getLizenz().get(i).getKey());
				if (lizenzTemp == null) {
					lizenzDao.insertLizenz(hardware.getLizenz().get(i));
				} else {
					lizenzService.checkLizenz(lizenzTemp);
					hardware.getLizenz().remove(i);
					hardware.getLizenz().add(lizenzTemp);
				}
			}

			hardwareDao.saveOrUpdateHardware(hardware);
			return hardware;
		}

	}

	@Override
	@Transactional
	public List<HardwareData> getAllHardware() {
		List<HardwareData> hardwareList = new ArrayList<HardwareData>();
		for (Hardware hardware : hardwareDao.getAllHardware()) {
			HardwareData hardwareData = new HardwareData();
			hardwareData.wrapperHardware(hardware);
			hardwareList.add(hardwareData);
		}

		return hardwareList;
	}

	@Override
	@Transactional
	public void loginHardware(String hostname, String username) {
		Hardware hardware = hardwareDao.getHardwareByName(hostname);
		hardware.setAktivusername(username);
		Date date = java.util.Calendar.getInstance().getTime();
		SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MM.yyyy - HH:mm:ss");
		hardware.setAktivdate(dateFormatter.format(date));
		hardwareDao.saveOrUpdateHardware(hardware);
	}

	@Override
	@Transactional
	public void logoutHardware(String hostname) {
		Hardware hardware = hardwareDao.getHardwareByName(hostname);
		hardware.setAktivusername("");
		hardware.setAktivdate("");
		hardwareDao.saveOrUpdateHardware(hardware);

	}

}
