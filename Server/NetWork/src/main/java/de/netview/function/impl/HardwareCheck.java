package de.netview.function.impl;

import java.io.IOException;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
			InetAddress iAdr;
			try {
				if (StringUtils.isEmpty(hardware.getIp())) {
					iAdr = InetAddress.getByName(hardware.getHostname()+".rsvx.it");
					if (!iAdr.getHostAddress().toLowerCase().contains("195.243.185.82")) {
					 hardware.setIp(iAdr.getHostAddress());
					}
				}else {
					iAdr = InetAddress.getByName(hardware.getIp());
				}
				
				if (iAdr.isReachable(1000)) {
					saveHardware(hardware, "green.png");
				} else {
					saveHardware(hardware, "red.png");
				}

			} catch (IOException e) {
				saveHardware(hardware, "unbekannt.png");
			}
		
	}

	private void saveHardware(Hardware hardware, String icon) {
		if (hardware.getIcon() == null || !hardware.getIcon().equals(icon)) {
			hardware.setIcon(icon);
			hardwareService.saveHardware(hardware);
		}
	}
}
