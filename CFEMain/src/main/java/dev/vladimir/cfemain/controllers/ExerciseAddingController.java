package dev.vladimir.cfemain.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.vladimir.cfecodemodule.baseclasses.Exercise;
import dev.vladimir.cfemain.feign.ExerciseServiceFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/ex/add")
@RequiredArgsConstructor
public class ExerciseAddingController {
    private final ObjectMapper objectMapper;
    private final ExerciseServiceFeignClient exerciseServiceFeignClient;

    @GetMapping()
    public String getExercisesAddingPage() {
        return "adding_exercise";
    }

    @PostMapping()
    public String addExercise(Model model, String json) {
        Exercise exercise;

        try {
            exercise = objectMapper.readValue(json, Exercise.class);
        } catch (Exception e) {
            model.addAttribute("result", e.getMessage());

            return "adding_exercise";
        }

        model.addAttribute("result", exercise.toString());
        exerciseServiceFeignClient.addExercise(exercise);

        return "adding_exercise";
    }
}
