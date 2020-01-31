package de.netview.dao;

import de.netview.model.Changelog;

import java.util.List;

public interface IChangelogDao {

    public Changelog addChangelog(Changelog changelog);
    public List getChangelogList();
    public List getChangelogListByHID(Long hid);


}
