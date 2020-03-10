package de.netview.service.Impl;

import de.netview.dao.IHardwareDAO;
import de.netview.dao.ISoftwareDao;
import de.netview.data.ADUserData;
import de.netview.data.AllInformation;
import de.netview.data.HardwareData;
import de.netview.data.HardwareInformation;
import de.netview.function.impl.IPSort;
import de.netview.model.Hardware;
import de.netview.model.Lizenz;
import de.netview.model.Software;
import de.netview.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class HardwareService implements IHardwareService {

    @Autowired
    private IHardwareDAO hardwareDao;

    @Autowired
    private ILizenzService lizenzService;

    @Autowired
    private ISoftwareDao softwareDao;

    @Autowired
    private ISoftwareService softwareService;

    @Autowired
    private IChangelogService changelogService;

    @Autowired
    private ILDAPService ldapService;

    @Transactional
    @Override
    public Hardware insertHardware(Hardware hardware) {
        SimpleDateFormat datum = new SimpleDateFormat();
        datum.applyPattern("EEEE', 'dd. MMMM yyyy");

        hardware.setIcon("unbekannt.png");
        hardware.setLastlogin(datum.format(new Date()));

        if (!StringUtils.isEmpty(hardware.getAktivusername())) {
            ADUserData userData = ldapService.getLDAPUserByName(hardware.getAktivusername());
            hardware.setDepartment(userData.getDepartment());
            hardware.setAktivuserphone(userData.getTelephoneNumber());
        }

        Hardware hardwareInfo = hardwareDao.getHardwareByName(hardware.getHostname());

        if (hardwareInfo != null) {

            readSoftware(hardware, hardwareInfo);
            readLizenz(hardware, hardwareInfo);
            cleanLizenz(hardware, hardwareInfo);

            hardwareInfo.wrappeValues(hardware);
            hardware = hardwareInfo;
        } else {
            hardware.setHid(null);
            insertSoftware(hardware);
            insertLizenz(hardware);
        }

        hardwareDao.saveOrUpdateHardware(hardware);

        return hardware;

    }

    private void cleanLizenz(Hardware hardware, Hardware hardwareInfo) {
        for (int i = 0; i < hardwareInfo.getLizenz().size(); i++) {
            boolean remove = true;
            for (int a = 0; a < hardware.getLizenz().size(); a++) {
                if (hardware.getLizenz().get(a).equals(hardwareInfo.getLizenz().get(i))) {
                    remove = false;
                    break;
                }
            }

            if (remove && ((hardwareInfo.getLizenz().get(i).getName().contains("Windows 10"))
                    || (hardwareInfo.getLizenz().get(i).getName().contains("Microsoft Office")))) {
                Lizenz removeLizenz = hardwareInfo.getLizenz().get(i);
                if (hardwareInfo.getLizenz().remove(removeLizenz)) {
                    checkLizenz(removeLizenz, 1);
                }
            }
        }
    }

    private void readLizenz(Hardware hardware, Hardware hardwareInfo) {
        for (int i = 0; i < hardware.getLizenz().size(); i++) {
            boolean insert = true;
            for (int a = 0; a < hardwareInfo.getLizenz().size(); a++) {
                if (hardwareInfo.getLizenz().get(a).equals(hardware.getLizenz().get(i))) {
                    insert = false;
                    break;
                }
            }

            if (insert) {
                Lizenz lizenzTemp = lizenzService.getLizenzByNameAndKey(hardware.getLizenz().get(i).getName(),
                        hardware.getLizenz().get(i).getKey());
                if (lizenzTemp == null) {
                    lizenzService.insertLizenz(hardware.getLizenz().get(i));
                    checkLizenz(hardware.getLizenz().get(i), 2);
                    hardwareInfo.getLizenz().add(hardware.getLizenz().get(i));
                } else {

                    checkLizenz(lizenzTemp, 2);
                    hardwareInfo.getLizenz().add(lizenzTemp);
                }
            }
        }
    }

    private void readSoftware(Hardware hardware, Hardware hardwareInfo) {
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
    }

    public void insertSoftware(Hardware hardware) {
        List<Software> resultSoftware = new ArrayList<>();
        for (Software software : hardware.getSoftware()) {
            Software softwareTemp = softwareDao.getSoftwareByName(software.getName());
            if (softwareTemp == null) {
                softwareDao.insertSoftware(software);
                resultSoftware.add(software);
            } else {
                resultSoftware.add(softwareTemp);
            }
        }

        hardware.getSoftware().clear();
        hardware.getSoftware().addAll(resultSoftware);
    }

    public void insertLizenz(Hardware hardware) {
        List<Lizenz> resultLizenz = new ArrayList<>();
        for (int i = 0; i < hardware.getLizenz().size(); i++) {
            Lizenz lizenzTemp = lizenzService.getLizenzByNameAndKey(hardware.getLizenz().get(i).getName(),
                    hardware.getLizenz().get(i).getKey());

            if (lizenzTemp == null) {
                hardware.getLizenz().get(i).setState(1);
                lizenzService.insertLizenz(hardware.getLizenz().get(i));
                resultLizenz.add(hardware.getLizenz().get(i));
                checkLizenz(hardware.getLizenz().get(i), 2);
            } else {
                resultLizenz.add(lizenzTemp);
                checkLizenz(lizenzTemp, 2);
            }

        }

        hardware.getLizenz().clear();
        hardware.getLizenz().addAll(resultLizenz);
    }

    @Override
    @Transactional
    public List<HardwareInformation> getAllHardware(String categorie) {
        if ((AllInformation.getAllHardwareInformation().isEmpty() && categorie.equalsIgnoreCase("clients"))
				|| (AllInformation.getAllNetzHardwareInformation().isEmpty() && !categorie.equalsIgnoreCase("clients"))) {
            List<HardwareInformation> hardwareList = new ArrayList<>();
            for (Hardware hardware : hardwareDao.getAllHardware(categorie)) {
                HardwareInformation hardwareData = new HardwareInformation(hardware);
                hardwareList.add(hardwareData);
            }
            hardwareList = IPSort.sortHardware(hardwareList);

            if ("clients".equalsIgnoreCase(categorie)) AllInformation.setAllClientsHardwareInformation(hardwareList);
            else AllInformation.setAllNetzHardwareInformation(hardwareList);
        }

        return categorie.equalsIgnoreCase("clients") ? AllInformation.getAllHardwareInformation() : AllInformation.getAllNetzHardwareInformation();
    }

    @Override
    @Transactional
    public String getHostnameByOwnerLastLogin(String owner) {
        Hardware hardware = hardwareDao.getHardwareByOwnerLastLogin(owner);
        if (hardware == null) {
            return "";
        } else {
            return hardware.getHostname();
        }
    }

    @Override
    @Transactional
    public List<Hardware> getAllHardware() {
        return hardwareDao.getAllHardware();
    }

    @Override
    @Transactional
    public HardwareData getHardwareDataById(long hid) {
        Hardware hardware = hardwareDao.getHardwareById(hid);

        ADUserData userOwner = ldapService.getLDAPUserByName(hardware.getOwner());
        ADUserData userInUse = null;

        if (hardware.getOwner() != hardware.getAktivusername()) {
            userInUse = ldapService.getLDAPUserByName(hardware.getAktivusername());
        }

        HardwareData hardwareData = new HardwareData(userOwner, userInUse);
        hardwareData.setChangelogList(changelogService.getChangelogListByHID(hardware.getHid()));
        hardwareData.wrapperHardware(hardware);
        return hardwareData;
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

    @Override
    @Transactional
    public void deleteHardware(Long hid) {
        Hardware hardware = hardwareDao.getHardwareById(hid);
        for (Lizenz lizenz : hardware.getLizenz()) {
            checkLizenz(lizenz, 1);
        }
        hardwareDao.deleteHardware(hardware);
    }

    public void checkLizenz(Lizenz lizenz, int mod) {
        lizenzService.checkLizenz(lizenz, mod);
        lizenzService.updateLizenz(lizenz);
    }

    @Override
    @Transactional
    public void deleteHardwareLizenz(Long hid, Long lid) {
        Hardware hardware = hardwareDao.getHardwareById(hid);

        for (int i = 0; i < hardware.getLizenz().size(); i++) {
            if (hardware.getLizenz().get(i).getLid().longValue() == lid) {
                checkLizenz(hardware.getLizenz().get(i), 1);
                hardware.getLizenz().remove(i);
            }
        }

        hardwareDao.saveOrUpdateHardware(hardware);
    }

    @Override
    @Transactional
    public void addHardwareLizenz(Long hid, Long lid) {
        Hardware hardware = hardwareDao.getHardwareById(hid);
        Lizenz lizenz = lizenzService.getLizenzById(lid);
        checkLizenz(lizenz, 2);
        hardware.getLizenz().add(lizenz);
        hardwareDao.saveOrUpdateHardware(hardware);

    }

    @Override
    @Transactional
    public void changeHardwareOwner(Map value) {
        Hardware hardware = getHardwareById(Long.parseLong(value.get("hid").toString()));
        hardware.setOwner(value.get("uid").toString());
        saveHardware(hardware);
    }

    @Override
    @Transactional
    public Hardware archivHardware(long hid) {
        Hardware hardware = getHardwareById(hid);

        if (hardware.getStatus() == null || hardware.getStatus() == 1) {
            hardware.setStatus(0);
        } else {
            hardware.setStatus(1);
        }

        saveHardware(hardware);
        return hardware;
    }

    @Override
    @Transactional
    public Hardware getHardwareByHostname(String hostname) {
        return hardwareDao.getHardwareByName(hostname);
    }

    @Override
    @Transactional
    public Hardware getHardwareByOwner(String owner) {
        List hardwareList = hardwareDao.getHardwareByOwner(owner);
        if (hardwareList == null || hardwareList.isEmpty()) {
            return null;
        } else {
            return (Hardware) hardwareList.get(0);
        }

    }

    @Override
    @Transactional
    public List<Hardware> getHardwareByOwnerList(String owner) {
        List<Hardware> hardwareList = hardwareDao.getHardwareByOwner(owner);
        if (hardwareList == null || hardwareList.isEmpty()) {
            return new ArrayList<Hardware>();
        } else {
            return hardwareList;
        }

    }

    @Override
    @Transactional
    public List<Hardware> getHardwareByUserList(String user) {
        List<Hardware> hardwareList = hardwareDao.getHardwareByUser(user);
        if (hardwareList == null || hardwareList.isEmpty()) {
            return new ArrayList<Hardware>();
        } else {
            return hardwareList;
        }

    }

    @Override
    @Transactional
    public void saveHardware(Hardware hardware) {
        hardwareDao.saveOrUpdateHardware(hardware);
    }

    @Override
    @Transactional
    public Hardware getHardwareById(long hid) {
        return hardwareDao.getHardwareById(hid);
    }

}
