package de.netview.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.netview.service.ILocationService;

@RestController
@RequestMapping("/location")
public class LocationController {

	@Autowired 
	public ILocationService locationService;
	
    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public List getLocations() {
		return locationService.getLocation();
	}

}
