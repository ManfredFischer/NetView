package de.netview.dao.Impl;

import de.netview.dao.IChangelogDao;
import de.netview.dao.IContractDao;
import de.netview.dao.config.AbstractDao;
import de.netview.model.Changelog;
import de.netview.model.Contract;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ChangelogDao extends AbstractDao<Changelog> implements IChangelogDao {
    @Override
    public Changelog addChangelog(Changelog changelog) {
        return save(changelog);
    }

    @Override
    public List getChangelogList() {
        return get("Changelog");
    }

    @Override
    public List getChangelogListByHID(Long hid) {
        return getSession().createQuery("from Changelog where hid = :hid").setParameter("hid",hid).list();
    }
}
