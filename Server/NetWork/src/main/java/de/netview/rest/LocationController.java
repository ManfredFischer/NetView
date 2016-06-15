package de.netview.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import de.netview.model.Location;
import de.netview.service.IComponenteService;

@RequestMapping("/componente/location")
public class LocationController {

	@Autowired
	private IComponenteService componenteService;

	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody Location addLocation(@RequestBody Location location) {
		return componenteService.addLocation(location);
	}

	@RequestMapping(method = RequestMethod.PUT)
	public @ResponseBody void updateLocation(@RequestBody Location location) {
		componenteService.saveLocation(location);
	}

	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody List<Location> getLocation(@RequestParam(value = "lid", required = false) Long lid) {
		return componenteService.getLocationList(lid);
	}

	@RequestMapping(method = RequestMethod.DELETE)
	public @ResponseBody void deleteLocation(@RequestParam(value = "lid", required = true) Long lid) {
		componenteService.deleteLocation(lid);
	}

}
