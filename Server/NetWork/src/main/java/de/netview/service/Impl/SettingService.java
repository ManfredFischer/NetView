package de.netview.service.Impl;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.netview.dao.IADSettingDao;
import de.netview.data.SettingsData;
import de.netview.model.ADSetting;
import de.netview.service.ISettingService;

@Service
@Transactional
public class SettingService implements ISettingService{

	@Autowired
	private IADSettingDao settingDao;
	
	@Override
	public void insertSetting(SettingsData setting) {
		settingDao.insertOrUpdateADSetting(setting.getAd());
	}

	@Override
	public SettingsData getSettings() {
		SettingsData settingsData = new SettingsData();
		settingsData.setAd(settingDao.getADSettings());
		return settingsData;
	}

}
