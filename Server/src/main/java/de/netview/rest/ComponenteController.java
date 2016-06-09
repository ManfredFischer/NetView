package de.netview.rest;

import org.springframework.web.bind.annotation.*;

/**
 * Created by mf on 09.06.2016.
 */
@RestController
public class ComponenteController {

    @RequestMapping(value = "/hardware", method = RequestMethod.POST)
    public void message(@RequestBody String info) {

    }
}
