package de.netview.rest;

import java.util.List;

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

import de.netview.model.Handy;
import de.netview.service.IHandyService;

@RestController
@RequestMapping("/handy")
public class HandyController {
	
	@Autowired
	private IHandyService handyService;
	
	@PostMapping
	private @ResponseBody Handy addHandy(@RequestBody Handy handy) {
		return handyService.addHandy(handy);
	}
	
	@GetMapping
	private @ResponseBody List getHandy() {
		return handyService.getHandy();
	}
	
	@GetMapping("/{hid}")
	private @ResponseBody Handy getHandyById(@PathVariable Long hid) {
		return handyService.getHandyById(hid);
	}
	
	@PutMapping
	private @ResponseBody Handy putHandy(@RequestBody Handy handy) {
		return handyService.updateHandy(handy);
	}
	
	@DeleteMapping("/{hid}")
	private void deleteHandy(@PathVariable Long hid) {
		handyService.removeHandy(hid);		
	}

}
