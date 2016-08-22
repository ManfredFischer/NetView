package de.netview.rest;

import de.netview.model.Location;
import de.netview.service.ILocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by mf on 21.08.2016.
 */
@Controller
@RequestMapping("/location")
public class LocationController {

    @Autowired
    private ILocationService locationService;

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody Location createLocation(@RequestBody Location location){
        return locationService.createLocation(location);
    }

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody List<Location> getLocationsByUser(){
        return locationService.getLocationsByUser();
    }

    @RequestMapping(value = "/{lid}", method = RequestMethod.DELETE)
    public void removeLocationById(@PathVariable Long lid){
        locationService.removeLocationById(lid);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public void removeLocationList(@RequestParam List<Long> lids){
        locationService.removeLocations(lids);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public void updateLocation(@RequestBody Location location){
        locationService.updateLocation(location);
    }


}
