package de.netview.service.Impl;

import de.netview.dao.IChangelogDao;
import de.netview.data.AllInformation;
import de.netview.model.Changelog;
import de.netview.model.Hardware;
import de.netview.service.IChangelogService;
import de.netview.service.IHardwareService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
public class ChangelogService implements IChangelogService {

    @Autowired
    private IChangelogDao changelogDao;

    @Autowired
    private IHardwareService hardwareService;

    @Override
    @Transactional
    public Changelog addChangelog(Changelog changelog) {

        if (changelog.getHid() == null){
            Hardware hardwareByHostname = hardwareService.getHardwareByHostname(changelog.getSystem());
            if (hardwareByHostname != null)
                changelog.setHid(hardwareByHostname.getHid());
        }

        changelog.setUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        changelog.setDate(new Date().getTime());
        return changelogDao.addChangelog(changelog);
    }

    @Override
    @Transactional
    public List<Changelog> getChangelogList() {
        if (AllInformation.getChangelog().isEmpty()){
            AllInformation.setChangelog(changelogDao.getChangelogList());
        }
        return AllInformation.getChangelog();
    }


    @Override
    @Transactional
    public List<Changelog> getChangelogListByHID(Long hid) {
        return changelogDao.getChangelogListByHID(hid);
    }
}
