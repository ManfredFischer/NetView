package de.netview.service;

import java.util.List;
import java.util.Map;

import de.netview.data.SettingsData;

public interface ISettingService {
	public void insertSetting(SettingsData settings);
	public SettingsData getSettings();

}
