package dev.vladimir.codeservice.feignClients;

import dev.vladimir.cfecodemodule.baseclasses.Project;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "project_service", url = "localhost:91/project")
public interface ProjectServiceFeignClient {
    @GetMapping("get")
    Project getProject(@RequestParam Long projectId);
}
