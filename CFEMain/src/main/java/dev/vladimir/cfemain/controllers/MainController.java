package dev.vladimir.cfemain.controllers;

import dev.vladimir.cfecodemodule.baseclasses.Exercise;
import dev.vladimir.cfemain.feign.CodeServiceFeignClient;
import dev.vladimir.cfemain.feign.ExerciseServiceFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final CodeServiceFeignClient codeServiceFeignClient;
    private final ExerciseServiceFeignClient exerciseServiceFeignClient;

    @GetMapping
    public String getMainPage(Model model)  {
        model.addAttribute(
                "exerisesId",
                exerciseServiceFeignClient
                        .getAllExercises()
                        .stream()
                        .map(Exercise::getId)
                        .collect(Collectors.toList())
        );

        return "main";
    }

    @PostMapping
    public String runCodeInSandbox(Model model, String code) {
        if(code == null) return "main";

        model.addAttribute("code", code);
        model.addAttribute("console", codeServiceFeignClient.runSandboxCode(code));

        return "main";
    }
}
