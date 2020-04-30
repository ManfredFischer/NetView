package de.netview.service.Impl;

import de.netview.dao.IHardwareDAO;
import de.netview.dao.ISoftwareDao;
import de.netview.data.ADUserData;
import de.netview.data.AllInformation;
import de.netview.data.HardwareData;
import de.netview.data.HardwareInformation;
import de.netview.data.enums.HardwareStatus;
import de.netview.function.impl.DateUtil;
import de.netview.function.impl.IPSort;
import de.netview.model.Hardware;
import de.netview.model.Lizenz;
import de.netview.model.Location;
import de.netview.model.Software;
import de.netview.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static de.netview.data.enums.HardwareStatus.AKTIV;
import static de.netview.data.enums.HardwareStatus.ARCHIV;

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
    private ILocationService locationService;

    @Autowired
    private IChangelogService changelogService;

    @Autowired
    private ILDAPService ldapService;

    @Transactional
    @Override
    public Hardware insertHardware(Hardware hardware) {

        ADUserData userData = ldapService.getLDAPUserByName(hardware.getAktivusername());
        Hardware hardwareInfo = hardwareDao.getHardwareByName(hardware.getHostname());
        Location location = locationService.getLocationByCity(userData.getCity());

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

        hardware.setLastlogin(DateUtil.getDateNow());
        hardware.setStatus(AKTIV);

        if (!StringUtils.isEmpty(hardware.getAktivusername())) {
            if (checkAdminLogin(userData)) {
                hardware.setDepartment(userData.getDepartment());
                hardware.setAktivuserphone(userData.getTelephoneNumber());
                hardware.setAktivlocation(location.getLid().intValue());
            }
        }

        if (!StringUtils.isEmpty(hardware.getOwner())){
            if (checkAdminLogin(userData)) {
                hardware.setOwnerlocation(location.getLid().intValue());
            }
        } else {
            if (!StringUtils.isEmpty(hardware.getAktivusername())
                    && checkAdminLogin(userData)){
                hardware.setOwnerlocation(location.getLid().intValue());
                hardware.setOwner(hardware.getAktivusername());
                hardware.setOwnerphone("");
            }
        }

        hardwareDao.saveOrUpdateHardware(hardware);

        return hardware;

    }

    private boolean checkAdminLogin(ADUserData userData){
        return !userData.getDisplayName().toLowerCase().startsWith("a_") && !userData.getDisplayName().toLowerCase().startsWith("admin");
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


        List<HardwareInformation> getCachedHardware = new ArrayList<>();

        if (categorie.equalsIgnoreCase("clients")){
            getCachedHardware = AllInformation.getClients();
        }else {
            getCachedHardware = AllInformation.getServer();
        }

        if (getCachedHardware.isEmpty()) {
            List<HardwareInformation> hardwareList = new ArrayList<>();
            for (Hardware hardware : hardwareDao.getAllHardware(categorie)) {
                HardwareInformation hardwareData = new HardwareInformation(hardware);
                hardwareList.add(hardwareData);
            }

            if (categorie.equalsIgnoreCase("clients")){
                return AllInformation.setClients(IPSort.sortHardware(hardwareList));
            }else {
                return AllInformation.setServer(IPSort.sortHardware(hardwareList));
            }
        }

        return getCachedHardware;
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
        hardware.setLastlogin(DateUtil.getDateNow());
        hardwareDao.saveOrUpdateHardware(hardware);
    }

    @Override
    @Transactional
    public void logoutHardware(String hostname) {
        Hardware hardware = hardwareDao.getHardwareByName(hostname);
        hardware.setAktivusername("");
        hardware.setLastlogin(0L);
        hardwareDao.saveOrUpdateHardware(hardware);

    }

    @Override
    @Transactional
    public void deleteHardware(Long hid) {
        Hardware hardware = hardwareDao.getHardwareById(hid);
        if (hardware != null) {
            for (Lizenz lizenz : hardware.getLizenz()) {
                checkLizenz(lizenz, 1);
            }
            hardwareDao.deleteHardware(hardware);
        }
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
        if (!hardware.getAktivusername().equalsIgnoreCase(value.get("uid").toString())) {
            ADUserData user = ldapService.getLDAPUserByName(value.get("uid").toString());
            hardware.setOwnerphone(user.getTelephoneNumber());
        }
        saveHardware(hardware);
    }

    @Override
    @Transactional
    public void setHardwareStatus(long hid, HardwareStatus status) {
        Hardware hardware = getHardwareById(hid);

        if (status == ARCHIV) {
            hardware.setStatus(status);
            hardware.setLastlogin(0L);
            hardware.setAktivusername("");
            hardware.setAktivlocation(-1);
            hardware.setAktivuserphone("");
        } else {
            hardware.setStatus(status);
        }

        saveHardware(hardware);

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
