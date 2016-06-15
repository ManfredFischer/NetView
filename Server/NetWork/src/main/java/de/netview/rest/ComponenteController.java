package de.netview.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import de.netview.model.Location;
import de.netview.service.IComponenteService;

@Controller
@RequestMapping("/componente")
public class ComponenteController {

	@Autowired
	private IComponenteService componenteService;

	@RequestMapping(value = "/location", method = RequestMethod.POST)
	public @ResponseBody Location addLocation(@RequestBody Location location) {
		return componenteService.addLocation(location);
	}

	@RequestMapping(value = "/location", method = RequestMethod.GET)
	public @ResponseBody List<Location> getLocation(@RequestParam(value = "lid", required = false) Long lid) {
		return componenteService.getLocationList(lid);
	}

	@RequestMapping("/")
	public String index() {
		return "index";
	}
}
