package dev.vladimir.codeservice.controllers;

import dev.vladimir.cfecodemodule.baseclasses.CFEFile;
import dev.vladimir.cfecodemodule.baseclasses.Exercise;
import dev.vladimir.codeservice.feign.ExerciseServiceFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/code")
@RequiredArgsConstructor
public class CodeRunController {
    private final ExerciseServiceFeignClient exerciseServiceFeignClient;

    @PostMapping("/run-sandbox")
    public String runSandboxCode(@RequestBody String code) {
        return new CFEFile(code).out;
    }

    @PostMapping("/run-with-ex")
    public String runExerciseCode(@RequestBody String code, @RequestParam Integer exId) {
        Exercise exercise = exerciseServiceFeignClient.getById(exId);
        List<String> out = exercise.getExpectedOut();

        for (int i = 0; i < out.size(); i++) {
            String expectedOut = out.get(i).trim();
            String actualOut = new CFEFile(
                    code,
                    exercise.getActualVariablesIn().get(i)
            ).out.trim();

            if(!actualOut.equals(expectedOut)) {
                return String.format("Ошибка! Ожидалось: '%s', получено: '%s'", expectedOut, actualOut);
            }
        }

        return "Задача успешно выполнена!";
    }
}
