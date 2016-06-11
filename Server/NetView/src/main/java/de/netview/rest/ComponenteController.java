package de.netview.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import de.netview.servicei.IComponenteService;

@Controller
public class ComponenteController {
	
	@Autowired
	private IComponenteService componenteService;

	@RequestMapping(value="/hello", method = RequestMethod.GET)
	public @ResponseBody String hello(){
		return "{}";
	}
	
	@RequestMapping("/")
	public String index(){
		return "index";
	}
}
