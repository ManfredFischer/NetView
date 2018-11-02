package de.netview.dao;

import java.util.List;

import de.netview.model.ADSetting;

public interface IADSettingDao {

	public void insertOrUpdateADSetting(ADSetting setting);
	public ADSetting getADSettings();
}
