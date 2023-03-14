package dev.vladimir.codeservice.controllers;

import dev.vladimir.cfecodemodule.baseclasses.Project;
import dev.vladimir.codeservice.feignClients.CodeRunServiceFeignClient;
import dev.vladimir.codeservice.feignClients.ProjectServiceFeignClient;
import dev.vladimir.codeservice.feignClients.UserServiceFeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequestMapping("code")
public class RunController {
    private final UserServiceFeignClient userServiceFeignClient;
    private final ProjectServiceFeignClient projectServiceFeignClient;
    private final CodeRunServiceFeignClient codeRunServiceFeignClient;

    public RunController(UserServiceFeignClient userServiceFeignClient, ProjectServiceFeignClient projectServiceFeignClient, CodeRunServiceFeignClient codeRunServiceFeignClient) {
        this.userServiceFeignClient = userServiceFeignClient;
        this.projectServiceFeignClient = projectServiceFeignClient;
        this.codeRunServiceFeignClient = codeRunServiceFeignClient;
    }

    @GetMapping("run")
    public ResponseEntity<Object> runProject(
            @RequestParam Long projectId,
            @RequestParam String username,
            @RequestHeader(AUTHORIZATION) String authUuid
    ) {
        Boolean userValidate = userServiceFeignClient.validateUser(username, authUuid).getBody();
        if(Boolean.FALSE.equals(userValidate))
            return null;

        Project project = projectServiceFeignClient.getProject(projectId);
        if(project == null)
            return null;

        codeRunServiceFeignClient.start();

        return null;
    }
}
