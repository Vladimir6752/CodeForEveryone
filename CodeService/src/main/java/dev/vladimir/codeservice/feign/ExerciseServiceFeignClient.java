package dev.vladimir.codeservice.feign;

import dev.vladimir.cfecodemodule.baseclasses.Exercise;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "exercise-service", url = "localhost:90/exercise")
public interface ExerciseServiceFeignClient {
    @GetMapping("/get")
    Exercise getById(@RequestParam Integer exId);

    @PostMapping("/add")
    void addExercise(@RequestBody Exercise exercise);
}
