package de.netview.rest;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;

import de.netview.data.enums.HardwareStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import de.netview.data.HardwareData;
import de.netview.data.HardwareInformation;
import de.netview.model.Hardware;
import de.netview.service.IHardwareService;


@RestController
@RequestMapping("/hardware")
public class HardwareController {

	@Autowired
	private IHardwareService hardwareService;

	@PostMapping
	public Hardware setHardwareInformation(@RequestBody Hardware hardware) {		
		return hardwareService.insertHardware(hardware);
	}
	

	@GetMapping
	public @ResponseBody List getHardwareInformation(@RequestParam String categorie) {
		return hardwareService.getAllHardware(categorie);
	}
	
	@GetMapping("/{hid}")
	public @ResponseBody HardwareData getHardwareInformationById(@PathVariable Long hid) {
		return hardwareService.getHardwareDataById(hid);
	}
	
	@PostMapping("/login")
	public void loginHardwareInformation(@RequestBody HashMap data) {
		hardwareService.loginHardware(data.get("hostname").toString(), data.get("username").toString());
	}
	
	@PostMapping("/logout")
	public void logoutHardwareInformation(@RequestBody HashMap data) {
		hardwareService.logoutHardware(data.get("hostname").toString());
	}
	
	@DeleteMapping
	public void deleteHardware(@RequestParam Long hid) {
		hardwareService.deleteHardware(hid);
	}
	
	@DeleteMapping("/lizenz")
	public void deleteHardwareLizenz(@RequestParam Long lid, @RequestParam Long hid) {
		hardwareService.deleteHardwareLizenz(hid, lid);
	}
	
	@PostMapping("/lizenz")
	public void addHardwareLizenz(@RequestParam Long lid, @RequestParam Long hid) {
		hardwareService.addHardwareLizenz(hid, lid);
	}
	
	@PutMapping("/owner")
	public void changeHardwareOwner(@RequestBody HashMap data) {
		hardwareService.changeHardwareOwner(data);
	}

	@PutMapping("/status")
	public void changeHardwareStatus(@RequestParam Long hid,@RequestParam HardwareStatus status) {
		hardwareService.setHardwareStatus(hid,status);
	}

}
