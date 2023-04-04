package dev.vladimir.cfemain.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.vladimir.cfecodemodule.baseclasses.Exercise;
import dev.vladimir.cfecodemodule.utils.Variable;
import dev.vladimir.cfemain.feign.ExerciseServiceFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin/ex/add")
@RequiredArgsConstructor
public class ExerciseAddingController {
    private final ObjectMapper objectMapper;
    private final ExerciseServiceFeignClient exerciseServiceFeignClient;

    @GetMapping()
    public String getExercisesAddingPage(Model model) {
        model.addAttribute("exAmount", exerciseServiceFeignClient.getAllExercises().size() + 1);

        return "adding_exercise";
    }

    @PostMapping()
    public String addExercise(Model model, String json) {
        Exercise exercise;

        try {
            exercise = objectMapper.readValue(json, Exercise.class);
            handleArrayVariables(exercise);
        } catch (Exception e) {
            model.addAttribute("result", String.format("%s, %s",e.getMessage(), json));

            return getExercisesAddingPage(model);
        }

        model.addAttribute("result", String.format("Успешно, %s",exercise.toString()));

        exerciseServiceFeignClient.addExercise(exercise);
        return getExercisesAddingPage(model);
    }

    private void handleArrayVariables(Exercise exercise) {
        for (List<Variable> variables : exercise.getActualVariablesIn()) {
            for (int i = 0; i < variables.size(); i++) {
                Variable variable = variables.get(i);
                try {
                    variables.set(i, new Variable(
                            variable.name(),
                            variable.type(),
                            objectMapper.readValue(variable.value().toString(), ArrayList.class)
                            )
                    );
                } catch (Exception ignored) {}
            }
        }
    }
}
