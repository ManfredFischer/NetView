package de.netview.rest;

import de.netview.model.Systemuser;
import de.netview.service.ISystemUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/systemuser")
public class SystemUserController {

    @Autowired
    private ISystemUserService systemuserService;
	
	@PostMapping
    public Systemuser addSystemUser(@RequestBody Systemuser systemuser){
	    return  systemuserService.createUser(systemuser);
    }

}
