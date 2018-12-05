package de.netview.service.Impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.netview.dao.ILizenzDao;
import de.netview.dao.Impl.LizenzDao;
import de.netview.model.Lizenz;
import de.netview.service.ILizenzService;

@Service
public class LizenzService implements ILizenzService {

	@Autowired
	private ILizenzDao lizenzDao;

	@Override
	@Transactional
	public void insertLizenz(Lizenz lizenz) {

		Lizenz aktivLizenz = lizenzDao.getLizenzByName(lizenz.getName(), lizenz.getKey());

		if (aktivLizenz == null) {
			lizenz.setState(0);
			lizenzDao.insertLizenz(lizenz);
		} else {
			checkLizenz(aktivLizenz);
			lizenzDao.updateLizenz(aktivLizenz);
		}

	}
	
	@Override
	public void checkLizenz(Lizenz lizenz) {
		if (lizenz.getHardware().size() > 1) {
			lizenz.setState(2);
		} else if (lizenz.getHardware().size() == 1) {
			lizenz.setState(1);
		} else {
			lizenz.setState(1);
		}
	}

	@Override
	@Transactional
	public List<Lizenz> getLizenz() {
		return lizenzDao.getLizenz();
	}

}
