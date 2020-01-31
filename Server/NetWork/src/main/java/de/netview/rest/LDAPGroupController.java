package de.netview.rest;

import de.netview.model.LDAPGroup;
import de.netview.service.ILDAPGroupService;
import de.netview.service.ILDAPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/ldapgroup")
public class LDAPGroupController {


    @Autowired
    private ILDAPGroupService ldapGroupService;

    @Autowired
    ILDAPService ldapService;

    @GetMapping
    public @ResponseBody  List getGroups() {
        return ldapGroupService.getLDAPGroups();
    }

    @PostMapping
    public @ResponseBody LDAPGroup addLDAPGroup(@RequestBody LDAPGroup ldapGroup){
        return ldapGroupService.addLDAPGroups(ldapGroup);
    }

    @DeleteMapping
    public void deletLDAPGroup(@RequestBody LDAPGroup ldapGroup){
        ldapGroupService.deleteLDAPGroups(ldapGroup);
    }

    @GetMapping("/ad")
    public @ResponseBody List getLDAPGroups() {
        return ldapService.getLDAPGroups();
    }

}
