package de.netview.function.impl;

import de.netview.service.ILDAPService;
import liquibase.util.StringUtils;
import org.springframework.stereotype.Service;

import de.netview.config.BeanUtil;
import de.netview.function.IHardwareCheck;
import de.netview.model.Hardware;
import de.netview.service.IHardwareService;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;

import static de.netview.data.enums.HardwareStatus.*;

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

        Map hardwareInformation = ldapService.getHardwareByHostname(hardware.getHostname());

        if (hardwareInformation.isEmpty()) {
            hardware.setStatus(PRUEFEN);
        } else {
            hardware.setStatus(AKTIV);
            hardware.setLastlogin((Long.parseLong(hardwareInformation.get("lastlogon").toString()) / 10000L) - +11644473600000L);
            hardware.setBs(hardwareInformation.get("operatingSystem").toString());

            if (DateUtil.isDateBefor(hardware.getLastlogin(), -30)) {
                hardware.setStatus(ARCHIV);
            } else {
                if (StringUtils.isEmpty(hardware.getOwner())) {
                    hardware.setOwner(hardware.getAktivusername());
                }
            }
        }

        hardwareService.saveHardware(hardware);
    }
}
