package de.netview.config;

import de.netview.config.BeanUtil;
import de.netview.dao.IChangelogDao;
import de.netview.dao.IHardwareDAO;
import de.netview.dao.ILizenzDao;
import de.netview.data.ADUserData;
import de.netview.data.AllInformation;
import de.netview.data.HardwareInformation;
import de.netview.data.LizenzInformation;
import de.netview.function.impl.IPSort;
import de.netview.model.Hardware;
import de.netview.model.Lizenz;
import de.netview.service.ILDAPService;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate5.SessionHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.ArrayList;
import java.util.List;


@Service
public class InitServer {

    private SessionFactory sessionFactory;
    private ILizenzDao lizenzDao;
    private IHardwareDAO hardwareDao;
    private IChangelogDao changelogDao;

    public InitServer() {
        sessionFactory = BeanUtil.getBean(SessionFactory.class);
        hardwareDao = BeanUtil.getBean(IHardwareDAO.class);
        lizenzDao = BeanUtil.getBean(ILizenzDao.class);
        changelogDao = BeanUtil.getBean(IChangelogDao.class);
        loadServerDaten();
    }

    private void loadServerDaten() {

        Session session = sessionFactory.openSession();

        try {
            TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(session));

            loadHardware();
            loadLizenz();

            AllInformation.setChangelog(changelogDao.getChangelogList());


        } catch (Exception error) {
            error.printStackTrace();
        } finally {
            session.close();
            TransactionSynchronizationManager.unbindResource(sessionFactory);
        }
    }

    private void loadLizenz() {
        List result = new ArrayList<>();

        List<Lizenz> lizenzen = lizenzDao.getLizenz("-1");
        checkLizenz(lizenzen);
        for (Lizenz lizenz : lizenzen) {
            result.add(new LizenzInformation(lizenz));
        }
        AllInformation.setLizenz(result);
    }

    private void loadHardware() {
        List<HardwareInformation> hardwareList = new ArrayList<>();
        for (Hardware hardware : hardwareDao.getAllHardware("clients")) {
            HardwareInformation hardwareData = new HardwareInformation(hardware);
            hardwareData.setRentDetails();
            hardwareList.add(hardwareData);
        }

        AllInformation.setClients(IPSort.sortHardware(hardwareList));

        hardwareList = new ArrayList<>();
        for (Hardware hardware : hardwareDao.getAllHardware("sonstiges")) {
            HardwareInformation hardwareData = new HardwareInformation(hardware);
            hardwareData.setRentDetails();
            hardwareList.add(hardwareData);
        }
        hardwareList = IPSort.sortHardware(hardwareList);

        AllInformation.setServer(IPSort.sortHardware(hardwareList));
    }

    public void checkLizenz(List<Lizenz> lizenzen) {
        for (Lizenz lizenz : lizenzen) {
            int lizenzInUse = lizenz.getHardware().size();
            Integer allowLizenz = lizenz.getAllowamount() == null ? 1 : lizenz.getAllowamount();
            Integer reserved = 0;
            if (!StringUtils.isEmpty(lizenz.getReserved())){
                reserved = lizenz.getReserved().split(",").length;
            }
            lizenzInUse += reserved;

            if (reserved == allowLizenz){
                lizenz.setState(3);
            } else if (lizenzInUse < allowLizenz) {
                lizenz.setState(0);
            }else if (lizenzInUse > allowLizenz) {
                lizenz.setState(2);
            } else {
                if (reserved > 0){
                    lizenz.setState(3);
                } else {
                    lizenz.setState(1);
                }

            }
        }
    }
}

