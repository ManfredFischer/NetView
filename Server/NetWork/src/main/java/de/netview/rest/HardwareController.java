package de.netview.rest;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import de.netview.data.HardwareInput;
import de.netview.model.Hardware;
import de.netview.model.Lizenz;
import de.netview.service.IHardwareService;
import de.netview.service.ISoftwareService;

@RestController
@RequestMapping("/hardware")
public class HardwareController {

	@Autowired
	private IHardwareService hardwareService;

	@PostMapping
	public void setHardwareInformation(@RequestBody HardwareInput hardware) {
		hardwareService.insertHardware(hardware);
	}

	@GetMapping("/all")
	public @ResponseBody List getHardwareInformation() {
		return hardwareService.getAllHardware();
	}
	
	@PostMapping("/login")
	public void loginHardwareInformation(@RequestBody HashMap data) {
		hardwareService.loginHardware(data.get("hostname").toString(), data.get("username").toString());
	}
	
	@PostMapping("/logout")
	public void logoutHardwareInformation(@RequestBody HashMap data) {
		hardwareService.logoutHardware(data.get("hostname").toString());
	}

}
