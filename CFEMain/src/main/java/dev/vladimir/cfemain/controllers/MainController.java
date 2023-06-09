package dev.vladimir.cfemain.controllers;

import dev.vladimir.cfecodemodule.baseclasses.Exercise;
import dev.vladimir.cfemain.feign.CodeServiceFeignClient;
import dev.vladimir.cfemain.feign.ExerciseServiceFeignClient;
import dev.vladimir.cfemain.loggerbot.Logger;
import dev.vladimir.cfemain.user.models.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public String getMainPage(Model model, @AuthenticationPrincipal UserEntity user) {
        model.addAttribute(
                "exerisesId",
                exerciseServiceFeignClient
                        .getAllExercises()
                        .stream()
                        .map(Exercise::getId)
                        .collect(Collectors.toList())
        );
        model.addAttribute("user", user);

        Logger.log("getMainPage()");
        return "main";
    }

    @PostMapping
    public String runCodeInSandbox(Model model, @AuthenticationPrincipal UserEntity user, String code) {
        if (code == null || code.isEmpty()) return getMainPage(model, user);

        String outputConsole = codeServiceFeignClient.runSandboxCode(code);

        model.addAttribute("code", code);
        model.addAttribute("console", outputConsole);

        Logger.log(
                String.format(
                        "runCodeInSandbox()%n```%s```%nConsole: ```%s```",
                        code,
                        outputConsole
                )
        );
        return getMainPage(model, user);
    }
}
