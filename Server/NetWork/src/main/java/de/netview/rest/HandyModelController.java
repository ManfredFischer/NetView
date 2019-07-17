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

import de.netview.dao.IHandyModelDao;
import de.netview.model.HandyModel;
import de.netview.service.IHandyModelService;

@RestController
@RequestMapping("/handyModel")
public class HandyModelController {
	
	@Autowired
	private IHandyModelService handyModelService;

	@GetMapping
	private List getHandyModel() {
		return handyModelService.getHandyModel();
	}
	
	@GetMapping("/{hmid}")
	private @ResponseBody HandyModel getHandyModelById(@PathVariable Long hmid) {
		return null;
	}
	
	@PostMapping
	private @ResponseBody HandyModel addHandyModel(@RequestBody HandyModel handyModel) {
		return handyModelService.addHandyModel(handyModel);
	}
	
	@PutMapping
	private @ResponseBody HandyModel  putHandyModel(@RequestBody HandyModel handyModel) {
		return handyModelService.updateHandyModel(handyModel);
	}
	
	@DeleteMapping("/{hmid}")
	private void deleteHandyModel(@PathVariable Long hmid) {
		handyModelService.removeHandyModel(hmid);
	}
}
