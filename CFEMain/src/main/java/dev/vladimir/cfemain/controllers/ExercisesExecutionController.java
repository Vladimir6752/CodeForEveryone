package dev.vladimir.cfemain.controllers;

import dev.vladimir.cfemain.feign.CodeServiceFeignClient;
import dev.vladimir.cfemain.feign.ExerciseServiceFeignClient;
import dev.vladimir.cfemain.user.models.UserEntity;
import dev.vladimir.cfemain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/ex")
@RequiredArgsConstructor
public class ExercisesExecutionController {
    private final CodeServiceFeignClient codeServiceFeignClient;
    private final ExerciseServiceFeignClient exerciseServiceFeignClient;
    private final UserService userService;

    @GetMapping
    public String getExercisesExecutionPage(Model model, @AuthenticationPrincipal UserEntity user, @RequestParam Integer exId) {
        model.addAttribute("exercise", exerciseServiceFeignClient.getById(exId));
        model.addAttribute("isSolved", user.getSolvedExercisesId().contains(exId));

        return "executing_exercise";
    }

    @PostMapping()
    public String runExerciseCode(Model model, @AuthenticationPrincipal UserEntity user, @RequestParam Integer exId, String code) {
        model.addAttribute("code", code);

        String codeRunningOutput = codeServiceFeignClient.runExerciseCode(code, exId);

        if(codeRunningOutput.equals("Задача успешно выполнена!"))
            userService.addSolvedExerciseIdInUser(exId, user);

        model.addAttribute(
                "output",
                codeRunningOutput
        );

        return getExercisesExecutionPage(model, user, exId);
    }
}
