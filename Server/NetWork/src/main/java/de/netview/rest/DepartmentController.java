package de.netview.rest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/department")
public class DepartmentController {

	@PostMapping
	public void addDepartment() {
		
	}
	
	@PutMapping
	public void updateDepartment() {
		
	}      
}
