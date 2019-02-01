package de.netview.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import de.netview.data.LizenzInformation;
import de.netview.model.Lizenz;
import de.netview.service.ILizenzService;

@RestController
@RequestMapping("/lizenz")
public class LizenzController {
	
	@Autowired
	private ILizenzService lizenzService;
	
	@PostMapping
	public void addLizenz(@RequestBody Lizenz lizenz) {
		lizenzService.insertLizenz(lizenz);
	}
	
	@GetMapping
	public @ResponseBody List<LizenzInformation> getLizenz(@RequestParam String state){
		return lizenzService.getLizenz(state);
	}
	
	@PutMapping("/state")
	public @ResponseBody Lizenz updateLizenzState(@RequestBody Lizenz lizenz){
		return lizenzService.updateLizenzState(lizenz);
	}
	
	@DeleteMapping
	public void deleteLizenz(@RequestParam Long lid) {
		lizenzService.deleteLizenz(lid);
	}

}
