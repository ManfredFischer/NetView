package de.netview.rest;

import java.util.List;

import de.netview.data.MobileUserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import de.netview.model.MobileUser;
import de.netview.service.IMobileUserService;

@RestController
@RequestMapping("/mobileUser")
public class MobileUserController {
	
	@Autowired
	private IMobileUserService mobileUserService;
	
	@GetMapping
	private @ResponseBody List getMobileUser() {
		return mobileUserService.getMobileUser();
	}
	
	@GetMapping("/{muid}")
	private @ResponseBody MobileUser getMobileUserById(@PathVariable Long muid) {
		return mobileUserService.getMobileUserByID(muid);
	}
	
	@PostMapping
	private @ResponseBody MobileUser addMobileUser(@RequestBody MobileUser mobileUser) {
		return mobileUserService.addMobileUser(mobileUser);
	}
	
	@PutMapping
	private @ResponseBody MobileUser putMobileUser(@RequestBody MobileUser mobileUser) {
		return mobileUserService.updateMobileUser(mobileUser);
	}
	
	@DeleteMapping("/{muid}")
	private void deleteMobileUser(@PathVariable Long muid) {
		mobileUserService.deleteMobileUser(muid);
	}


}
