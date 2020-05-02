package de.netview.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import de.netview.data.WizardUser;
import de.netview.data.LDAPUserData;
import de.netview.model.LDAPUser;
import de.netview.service.IHardwareService;
import de.netview.service.ILDAPService;
import de.netview.service.ILocationService;

/**
 * Created by mf on 25.06.2016.
 */
@RestController
@RequestMapping("/ldap/user")
public class LDAPUserController {

	@Autowired
	ILDAPService LDAPService;
	
	@Autowired
	IHardwareService hardwareService;

	@Autowired
	ILocationService LocationService;

	@GetMapping
	public @ResponseBody List getLDAPADUsers() {
		return LDAPService.getLDAPADUsers();
	}

	@PutMapping("/ad")
	public void updateLDAPADUsers(@RequestBody WizardUser wizardUser) throws Exception {
		LDAPService.updateLDAPUser(wizardUser);
	}

	@PostMapping("/ad")
	public void createLDAPADUsers(@RequestBody WizardUser wizardUser) throws Exception {
		LDAPService.createLDAPNewUser(wizardUser);
	}
	
	@GetMapping("/{uid}")
	public @ResponseBody LDAPUserData getUser(@PathVariable String uid) {
		return LDAPService.getLDAPUserData(uid);
	}

	@GetMapping("/{username}/hostname")
	public @ResponseBody Map getUserHardware(@PathVariable String username) {
		HashMap userInfo = new HashMap();
		userInfo.put("hostname", hardwareService.getHostnameByOwnerLastLogin(username));
		return userInfo;
	}
	
	@PostMapping
	public void createLDAPUsers(@RequestBody LDAPUser ldapUser) throws Exception {
		LDAPService.addOrUpdateLDAPUser(ldapUser);
	}
	
	@PutMapping("/rent")
	public void rentHardwareForLDAPUsers(@RequestBody Map value) throws Exception {
		LDAPService.rentHardwareForLDAPUser(value);
	}
	
	@PutMapping("/rent/back")
	public void getBackHardwareForLDAPUsers(@RequestBody Map value) throws Exception {
		LDAPService.getBackHardwareFromLDAPUser(value);
	}

}
