package dev.vladimir.projectservice.controllers;

import dev.vladimir.cfecodemodule.baseclasses.Project;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("project")
public class GetProjectController {
    @GetMapping("get")
    public Project getProject(@RequestParam Long projectId) {
        return null;
    }
}
