package dev.vladimir.cfemain.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "code-service", url = "localhost:81/code")
public interface CodeServiceFeignClient {
    @PostMapping("/run-sandbox")
    String runSandboxCode(@RequestBody String code);

    @PostMapping("/run-with-ex")
    String runExerciseCode(@RequestBody String code, @RequestParam Integer exId);
}
