package de.netview.service;

import java.util.List;
import java.util.Map;

import de.netview.data.HardwareData;
import de.netview.data.HardwareInput;
import de.netview.model.Hardware;
import de.netview.model.Lizenz;

public interface IHardwareService {

	public Hardware insertHardware(Hardware hardware);
	public List<HardwareData> getAllHardware();
	public void loginHardware(String hostname, String username);
	public void logoutHardware(String hostname );
}
