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
import de.netview.service.ISoftwareService;

@Service
@Transactional
public class HardwareService implements IHardwareService {

	@Autowired
	private ISoftwareService softwareService;

	@Autowired
	private IHardwareDAO hardwareDao;

	@Autowired
	private ILizenzDao lizenzDao;

	@Override
	public void insertHardware(HardwareInput hardware) {
		Hardware checkHardware = null;
		Boolean update = false;
		if (hardware.getOldHostname() == null) {
			checkHardware = hardwareDao.getHardwareByName(hardware.getHostname());
			if (checkHardware == null) {
				List<Software> resultSoftware = new ArrayList<>();
				
				for (Software software : hardware.getHardware().getSoftware()) {
					resultSoftware.add(softwareService.insertSoftware(software));
				}
				
				hardware.getHardware().getSoftware().clear();
				hardware.getHardware().setSoftware(resultSoftware);

				for (Lizenz lizenz : hardware.getHardware().getLizenz()) {
					lizenzDao.insertLizenz(lizenz);
				}
								
				checkHardware = hardware.getHardware();
			} else {
				update = true;
			}
		} else {
			checkHardware = hardwareDao.getHardwareByName(hardware.getOldHostname());
			update = true;
		}

		if (update) {
			wrapperUpdateHardware(hardware.getHardware(), checkHardware);
		}
		
		checkHardware.setOwner(hardware.getOwner());
		hardwareDao.saveOrUpdateHardware(checkHardware);
	}

	private void wrapperUpdateHardware(Hardware hardwareOuelle, Hardware hardwareSource) {
		hardwareSource.setBs(hardwareOuelle.getBs());
		hardwareSource.setCpu(hardwareOuelle.getCpu());
		hardwareSource.setHostname(hardwareOuelle.getHostname());
		hardwareSource.setModel(hardwareOuelle.getModel());
		hardwareSource.setRam(hardwareOuelle.getRam());
		hardwareSource.setSn(hardwareOuelle.getSn());
		hardwareSource.setOwner(hardwareOuelle.getOwner());
		
		for (Lizenz lizenzSource : hardwareSource.getLizenz()) {
			boolean insert = true;
			for (Lizenz lizenzQuelle : hardwareOuelle.getLizenz()) {
				if (lizenzSource.equals(lizenzQuelle)) {
					insert = false;
					break;
				}
			}

			if (insert) {
				Lizenz newLizenz = new Lizenz();
				lizenzDao.insertLizenz(newLizenz);
				hardwareSource.addLizenz(newLizenz);
			}
		}
		
		for (Lizenz lizenzQuelle : hardwareOuelle.getLizenz()) {
			boolean remove = true;
			for (Lizenz lizenzSource : hardwareSource.getLizenz()) {
				if (lizenzQuelle.equals(lizenzSource)) {
					remove = false;
					break;
				}
			}

			if (remove) {
				hardwareSource.getLizenz().remove(lizenzQuelle);
			}
		}
	}

	@Override
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
	public void loginHardware(String hostname, String username) {
		Hardware hardware = hardwareDao.getHardwareByName(hostname);
		hardware.setAktivusername(username);
		Date date = java.util.Calendar.getInstance().getTime();
		SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MM.yyyy - HH:mm:ss");
		hardware.setAktivdate(dateFormatter.format(date));
		hardwareDao.saveOrUpdateHardware(hardware);
	}

	@Override
	public void logoutHardware(String hostname) {
		Hardware hardware = hardwareDao.getHardwareByName(hostname);
		hardware.setAktivusername("");
		hardware.setAktivdate("");
		hardwareDao.saveOrUpdateHardware(hardware);
		
	}

}
