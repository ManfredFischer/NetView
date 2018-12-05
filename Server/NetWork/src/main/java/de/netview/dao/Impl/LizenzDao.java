package de.netview.dao.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import de.netview.dao.ILizenzDao;
import de.netview.dao.config.AbstractDao;
import de.netview.model.Lizenz;
import de.netview.model.Software;

@Repository
public class LizenzDao extends AbstractDao<Lizenz> implements ILizenzDao {

	@Override
	public void insertLizenz(Lizenz lizenz) {
		getSession().persist(lizenz);		
		getSession().flush();
	}

	@Override
	public Lizenz getLizenzByName(String name, String key) {
		List<Lizenz> lizenzList = getSession().createQuery("from Lizenz where name = :name and key= :key").setParameter("name", name).setParameter("key", key).list();
		if (lizenzList != null) {
			if (lizenzList.size() == 1) {
				return lizenzList.get(0);
			}
		}
		return null;
	}

	@Override
	public List<Lizenz> getLizenz() {
		List<Lizenz> lizenzList = getSession().createQuery("from Lizenz").list();
		if (lizenzList != null) {
			return lizenzList;
		}
		return new ArrayList();
	}
	
	public int getLizenzState(Lizenz lizenz) {
		List lizenzList = getSession().createQuery("from hardware_lizenz where lid = :lid").setParameter("lid", lizenz.getLid()).list();
		
		if (lizenzList.size() > 1) {
			return 2;
		}else {
			return 1;
		}
	}



	@Override
	public void updateLizenz(Lizenz lizenz) {
		getSession().update(lizenz);		
		getSession().flush();
	}
}
