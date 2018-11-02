package de.netview.dao.Impl;

import java.util.List;

import org.springframework.stereotype.Component;

import de.netview.dao.IADSettingDao;
import de.netview.dao.config.AbstractDao;
import de.netview.model.ADSetting;
@Component
public class ADSettingDao extends AbstractDao<ADSetting> implements IADSettingDao{

	@Override
	public void insertOrUpdateADSetting(ADSetting setting) {
		getSession().saveOrUpdate(setting);
	}

	@Override
	public ADSetting getADSettings() {
		List<ADSetting> result = getSession().createQuery("from ADSetting").list();
		if (result == null || result.isEmpty()) {
			return null;
		}else {
		  return result.get(0);	
		}
		
	}

}
