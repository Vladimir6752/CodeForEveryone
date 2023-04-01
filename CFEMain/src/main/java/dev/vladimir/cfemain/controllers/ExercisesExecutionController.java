package dev.vladimir.cfemain.controllers;

import dev.vladimir.cfemain.feign.CodeServiceFeignClient;
import dev.vladimir.cfemain.feign.ExerciseServiceFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/ex")
@RequiredArgsConstructor
public class ExercisesExecutionController {
    private final CodeServiceFeignClient codeServiceFeignClient;
    private final ExerciseServiceFeignClient exerciseServiceFeignClient;

    @GetMapping
    public String getExercisesExecutionPage(Model model, @RequestParam Integer exId) {
        model.addAttribute("exercise", exerciseServiceFeignClient.getById(exId));

        return "executing_exercise";
    }

    @PostMapping()
    public String runExerciseCode(Model model, @RequestParam Integer exId, String code) {
        model.addAttribute("code", code);
        model.addAttribute("exercise", exerciseServiceFeignClient.getById(exId));

        model.addAttribute(
                "output",
                codeServiceFeignClient.runExerciseCode(code, exId)
        );

        return "executing_exercise";
    }
}
