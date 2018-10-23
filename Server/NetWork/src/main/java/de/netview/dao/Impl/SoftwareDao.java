package de.netview.dao.Impl;

import java.util.List;

import org.postgresql.util.PSQLException;
import org.springframework.stereotype.Repository;

import de.netview.dao.ISoftwareDao;
import de.netview.dao.config.AbstractDao;
import de.netview.model.Software;

@Repository
public class SoftwareDao extends AbstractDao<Software> implements ISoftwareDao {

	@Override
	public void insertSoftware(Software software) {
		getSession().saveOrUpdate(software);
	}

	@Override
	public Software getSoftwareByName(String name) {
		List<Software> softwareList = getSession().createQuery("from Software where name = :name").setParameter("name", name).list();
		if (softwareList != null) {
			if (softwareList.size() == 1) {
				return softwareList.get(0);
			}
		}
		return null;
	}

}
