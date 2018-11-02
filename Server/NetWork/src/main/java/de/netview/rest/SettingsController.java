package de.netview.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import de.netview.data.SettingsData;
import de.netview.service.ISettingService;

@RestController
@RequestMapping("/settings")
public class SettingsController {

	@Autowired
	private ISettingService settingService;
	
	@GetMapping
	public @ResponseBody SettingsData getSettins() {
		return settingService.getSettings();
	}
	
	@PostMapping
	public void setSettings(@RequestBody SettingsData setting) {
		settingService.insertSetting(setting);
	}
}
