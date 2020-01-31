package de.netview.rest;

import de.netview.model.Changelog;
import de.netview.service.IChangelogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/changelog")
public class ChangelogController {

    @Autowired
    private IChangelogService changelogService;

    @PostMapping
    public Changelog addChangelog(@RequestBody Changelog changelog){
        return changelogService.addChangelog(changelog);
    }

    @GetMapping
    public List getChangeLogList(){
        return changelogService.getChangelogList();
    }
}
