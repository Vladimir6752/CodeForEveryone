package dev.vladimir.cfemain.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.vladimir.cfemain.feign.ExerciseServiceFeignClient;
import dev.vladimir.cfemain.loggerbot.Logger;
import dev.vladimir.cfemain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final ExerciseServiceFeignClient exerciseServiceFeignClient;
    private final UserService userService;

    @GetMapping()
    public String getAdminPage(Model model) {
        model.addAttribute("exercises", exerciseServiceFeignClient.getAllExercises());

        Logger.log("getAdminPage()");
        return "admin";
    }

    @PostMapping("delete")
    public String deleteExercise(Model model, Integer exId) {
        exerciseServiceFeignClient.deleteWhereId(exId);

        userService.deleteSolvedExerciseIdFromUsers(exId);

        Logger.log(String.format("deleteExercise(%d)", exId));
        return getAdminPage(model);
    }

    @GetMapping("ex/download")
    public String sendAllExToAdmin(Model model) {
        try {
            Logger.getLoggerBot().sendDocument(
                    new ObjectMapper().writeValueAsString(
                            exerciseServiceFeignClient.getAllExercises()
                    )
            );
        } catch (JsonProcessingException e) {
            Logger.log("Failed to send document with error: " + e.getMessage());
        }
        return getAdminPage(model);
    }
}
