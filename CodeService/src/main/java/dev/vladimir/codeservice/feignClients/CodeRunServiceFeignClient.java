package dev.vladimir.codeservice.feignClients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "code_run_service", url = "localhost:92/project")
public interface CodeRunServiceFeignClient {
    @GetMapping("start")
    Object start();
}
