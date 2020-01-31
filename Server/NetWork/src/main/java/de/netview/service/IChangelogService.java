package de.netview.service;

import de.netview.model.Changelog;

import javax.transaction.Transactional;
import java.util.List;

public interface IChangelogService {

    public Changelog addChangelog(Changelog changelog);
    public List<Changelog> getChangelogList();

    @Transactional
    List<Changelog> getChangelogListByHID(Long hid);
}
