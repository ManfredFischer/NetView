package de.netview.function.impl;

import org.springframework.stereotype.Service;

import de.netview.config.BeanUtil;
import de.netview.function.IHardwareCheck;
import de.netview.model.Hardware;
import de.netview.service.IHardwareService;

@Service
public class HardwareCheck implements IHardwareCheck {

	private IHardwareService hardwareService;
	
	public HardwareCheck() {
		hardwareService = BeanUtil.getBean(IHardwareService.class);
	}
	
	
	

	@Override
	public void checkHostname(Hardware hardware) {


	}

	private void saveHardware(Hardware hardware, String icon) {
		if (hardware.getIcon() == null || !hardware.getIcon().equals(icon)) {
			hardware.setIcon(icon);
			hardwareService.saveHardware(hardware);
		}
	}
}
