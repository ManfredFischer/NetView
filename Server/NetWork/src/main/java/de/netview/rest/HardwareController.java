package de.netview.rest;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import de.netview.model.Hardware;
import de.netview.service.IHardwareService;


@RestController
@RequestMapping("/hardware")
public class HardwareController {

	@Autowired
	private IHardwareService hardwareService;

	@PostMapping
	public Hardware setHardwareInformation(@RequestBody Hardware hardware) {
		hardware = hardwareService.insertHardware(hardware);
		
		return hardware;
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
