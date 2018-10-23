package de.netview.dao.Impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import de.netview.dao.ILizenzDao;
import de.netview.dao.config.AbstractDao;
import de.netview.model.Lizenz;

@Repository
public class LizenzDao extends AbstractDao<Lizenz> implements ILizenzDao {

	@Override
	public void insertLizenz(Lizenz hardwareLizenz) {
		getSession().saveOrUpdate(hardwareLizenz);		
	}

	@Override
	public String checkLizenzByHostnameAndLizenz(Lizenz hardwareLizenz) {
			return "0";
	}
}
