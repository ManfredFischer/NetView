package de.netview.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.netview.data.ADUserData;
import de.netview.data.ADUserUpdateData;
import de.netview.model.Location;
import de.netview.service.ILDAPService;
import de.netview.service.ILocationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mf on 25.06.2016.
 */
@RestController
@RequestMapping("/ldap/user")
public class LDAPUserController {

    @Autowired
    ILDAPService LDAPService;
    
    @Autowired
    ILocationService LocationService;

    @GetMapping
    public @ResponseBody List getUsers() {
    	ArrayList<ADUserData> result = new ArrayList<>();
    	ArrayList<ADUserData> ADUserDataList = (ArrayList<ADUserData>) LDAPService.getUsers();
    	ArrayList<Location> LocationList = (ArrayList<Location>) LocationService.getLocation();
    	
    	for (ADUserData aDUserdata : ADUserDataList) {
    		for (Location location : LocationList) {
        		if (location.getCity().equals(aDUserdata.getCity()) && location.getStreet().equals(aDUserdata.getStreetAddress())) {
        			aDUserdata.setLid(location.getLid());
        			break;
        		}
        	}
    		
    		result.add(aDUserdata);
    	}
    	
        return result;
    }
    
    @PutMapping
    public void updateUsers(@RequestBody ADUserUpdateData adUserUpdateData) throws Exception {
    	LDAPService.updateUser(adUserUpdateData);
    }
    
    @PostMapping
    public void createUsers(@RequestBody ADUserUpdateData adUserUpdateData) throws Exception {
    	LDAPService.createNewUser(adUserUpdateData);
    }


}