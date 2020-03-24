package de.netview.service.Impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import de.netview.model.Hardware;
import de.netview.model.Lizenz;
import de.netview.model.Location;
import de.netview.service.IHardwareService;
import de.netview.service.IImportService;
import de.netview.service.ILizenzService;
import de.netview.service.ILocationService;

@Service
public class ImportService implements IImportService {

	@Autowired
	private IHardwareService hardwareService;

	@Autowired
	private ILizenzService lizenzService;

	@Autowired
	private ILocationService locationService;

	@Override
	public List importHardware(MultipartFile file) throws IOException {
		String content = new String(file.getBytes());
		String[] rows = content.split("\\r\\n");
		ArrayList<String> excists = new ArrayList<String>();

		for (String values : rows) {
			String[] attribute = values.split(";");

			if (attribute[0].equalsIgnoreCase("Hostname") || attribute[0].equalsIgnoreCase("Rechnername"))
				continue;
			if (attribute.length != 13)
				continue;

			if (hardwareService.getHardwareByHostname(attribute[0]) != null) {
				excists.add(attribute[0]);
				continue;
			}
			Hardware hardware = new Hardware();

			try {
				hardware.setHostname(attribute[0]);
				hardware.setIp(attribute[1]);
				hardware.setModel(attribute[2]);
				hardware.setSn(attribute[3]);
				hardware.setCpu(attribute[4]);
				hardware.setRam(attribute[5]);
				hardware.setStorage(attribute[6]);
				hardware.setOwner(attribute[7]);
				hardware.setDescription(attribute[8]);
				hardware.setMac(attribute[9]);

				hardware.setOwnerlocation(locationService.getLocationByCity(attribute[10].toString()).getLid().intValue());

				hardware.setCategorie(attribute[11]);
				hardware.setBs(attribute[12]);
			} catch (Exception e) {
				continue;
			}

			hardwareService.insertHardware(hardware);

		}

		return excists;
	}

	@Override
	public List importLizenzen(MultipartFile file) throws IOException {
		String content = new String(file.getBytes());
		String[] rows = content.split("\\r\\n");
		ArrayList<String> excists = new ArrayList<String>();

		for (String values : rows) {
			try {
				String[] attribute = values.split(";");

				if (attribute.length != 5)
					continue;
				if (attribute[0].equalsIgnoreCase("Name"))
					continue;
				Lizenz lizenzUpdate = lizenzService.getLizenzByNameAndKey(attribute[0], attribute[1]);
				if (lizenzUpdate != null) {
					lizenzUpdate.setState(Integer.parseInt(attribute[3]));
					lizenzUpdate.setReserved(attribute[4]);
					lizenzService.updateLizenz(lizenzUpdate);
				}else {
					Lizenz lizenz = new Lizenz();
					lizenz.setName(attribute[0]);
					lizenz.setKey(attribute[1]);
					lizenz.setCategorie(attribute[2]);
					lizenz.setState(Integer.parseInt(attribute[3]));
					lizenz.setReserved(attribute[4]);
					lizenzService.insertLizenz(lizenz);
				}

				
			} catch (Exception e) {
				System.out.println(e);
			}

		}

		return excists;
	}

	@Override
	public List importMobile(MultipartFile file) {
		return null;
	}

}
