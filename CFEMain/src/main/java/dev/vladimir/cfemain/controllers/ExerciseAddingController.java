package dev.vladimir.cfemain.controllers;

import dev.vladimir.cfecodemodule.baseclasses.Exercise;
import dev.vladimir.cfemain.exercise.ExerciseJsonHandler;
import dev.vladimir.cfemain.feign.ExerciseServiceFeignClient;
import dev.vladimir.cfemain.loggerbot.Logger;
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
    private final ExerciseServiceFeignClient exerciseServiceFeignClient;
    private final ExerciseJsonHandler exerciseJsonHandler;

    @GetMapping()
    public String getExercisesAddingPage(Model model) {
        model.addAttribute("exId", exerciseServiceFeignClient.getAllExercises().size() + 1);

        Logger.log("getExercisesAddingPage()");
        return "adding_exercise";
    }

    @PostMapping()
    public String addExercise(Model model, String json) {
        Exercise exercise = exerciseJsonHandler.handleExerciseJson(model, json);

        if(exercise != null) {
            Logger.log("addExercise(): " + exercise);
            exerciseServiceFeignClient.addExercise(exercise);
        }

        return getExercisesAddingPage(model);
    }
}
