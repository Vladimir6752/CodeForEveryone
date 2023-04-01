package dev.vladimir.cfemain.controllers;

import dev.vladimir.cfemain.feign.ExerciseServiceFeignClient;
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

    @GetMapping()
    public String getAdminPage(Model model) {
        model.addAttribute("exercises", exerciseServiceFeignClient.getAllExercises());

        return "admin";
    }

    @PostMapping("delete")
    public String deleteExercise(Model model, Integer exId) {
        exerciseServiceFeignClient.deleteWhereId(exId);

        return getAdminPage(model);
    }
}
