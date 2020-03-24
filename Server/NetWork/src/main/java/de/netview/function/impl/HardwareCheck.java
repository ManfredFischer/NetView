package de.netview.function.impl;

import de.netview.service.ILDAPService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import de.netview.config.BeanUtil;
import de.netview.function.IHardwareCheck;
import de.netview.model.Hardware;
import de.netview.service.IHardwareService;

import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.Date;

import static de.netview.data.enums.HardwareStatus.AKTIV;
import static de.netview.data.enums.HardwareStatus.ARCHIV;

@Service
public class HardwareCheck implements IHardwareCheck {

	private IHardwareService hardwareService;
	private ILDAPService ldapService;
	
	public HardwareCheck() {
		hardwareService = BeanUtil.getBean(IHardwareService.class);
		ldapService = BeanUtil.getBean(ILDAPService.class);
	}
	
	
	

	@Override
	public void checkHostname(Hardware hardware) {

		hardware = ldapService.checkHardwareByDomain(hardware);

		if (hardware.getStatus() == AKTIV.getValue()) {

			StringUtils.isEmpty(hardware.getLastlogin());
			long fileTime = (Long.parseLong(hardware.getLastlogin()) / 10000L) - + 11644473600000L;

			if (new Date(fileTime).toInstant().isBefore(ZonedDateTime.now().plusDays(-30).toInstant())) {
				hardware.setStatus(ARCHIV.getValue());
			}else {
				if (StringUtils.isEmpty(hardware.getOwner())) {
					hardware.setOwner(hardware.getAktivusername());
				}
			}
		}

		hardwareService.saveHardware(hardware);

	}

}
