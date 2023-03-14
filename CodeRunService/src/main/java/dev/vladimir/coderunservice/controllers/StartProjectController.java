package dev.vladimir.coderunservice.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("project")
public class StartProjectController {
    @GetMapping("start")
    public Object start() {
        return null;
    }
}
