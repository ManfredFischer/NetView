package de.netview.service.Impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.netview.dao.ILizenzDao;
import de.netview.dao.Impl.LizenzDao;
import de.netview.data.LizenzInformation;
import de.netview.model.Lizenz;
import de.netview.service.ILizenzService;

@Service
public class LizenzService implements ILizenzService {

	@Autowired
	private ILizenzDao lizenzDao;

	@Override
	@Transactional
	public void insertLizenz(Lizenz lizenz) {
		lizenzDao.insertLizenz(lizenz);
	}
	
	@Transactional
	@Override
	public Lizenz updateLizenzState(Lizenz lizenz) {
		if (lizenz.getState() == 0) {
			checkLizenz(lizenz,1);
		}else {
			checkLizenz(lizenz,2);
		}
		
		lizenzDao.updateLizenz(lizenz);
		
		return lizenz;
	}
	

	@Override
	public void checkLizenz(Lizenz lizenz, int modus) {
		int hardwareCount = lizenz.getHardware().size();

		if (modus == 1) {
			hardwareCount--;
		} else if (modus == 2) {
			hardwareCount++;
		}

		if (hardwareCount > 1) {
			lizenz.setState(2);
		} else if (hardwareCount == 1) {
			lizenz.setState(1);
		} else {
			lizenz.setState(0);
		}
	}

	@Override
	@Transactional
	public List<LizenzInformation> getLizenz(String state) {
		List<LizenzInformation> result = new ArrayList<>();
		
		List<Lizenz> lizenzen = lizenzDao.getLizenz(state);
		
		for (Lizenz lizenz : lizenzen) {
			result.add(new LizenzInformation(lizenz));
		}
		
		return result;
	}

	@Override
	@Transactional
	public void updateLizenz(Lizenz lizenz) {
		lizenzDao.updateLizenz(lizenz);
	}

	@Override
	@Transactional
	public void deleteLizenz(Long lid) {
		Lizenz lizenz = lizenzDao.getLizenzById(lid);
		lizenzDao.deleteLizenz(lizenz);
	}

	@Override
	@Transactional
	public Lizenz getLizenzById(Long lid) {
		return lizenzDao.getLizenzById(lid);
	}



	@Override
	@Transactional
	public void insertAndCheckLizenz(Lizenz lizenz) {
		Lizenz aktivLizenz = lizenzDao.getLizenzByName(lizenz.getName(), lizenz.getKey());

		if (aktivLizenz == null) {
			lizenzDao.insertLizenz(lizenz);
		} else {
			checkLizenz(aktivLizenz, -1);
			lizenzDao.updateLizenz(aktivLizenz);
		}
	}



	@Override
	@Transactional
	public Lizenz getLizenzByNameAndKey(String Name, String Key) {
		return lizenzDao.getLizenzByName(Name, Key);
	}

}
