package dev.vladimir.exerciseservice.controllers;

import dev.vladimir.cfecodemodule.baseclasses.Exercise;
import dev.vladimir.exerciseservice.config.ExercisesData;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/exercise")
public class ExerciseController {
    @GetMapping("/get-all")
    public List<Exercise> getAllExercises() {
        return ExercisesData.allExercises;
    }

    @GetMapping("/get")
    public Exercise getById(@RequestParam Integer exId) {
        return ExercisesData
                .allExercises
                .stream()
                .filter(exercise -> exercise.getId() == exId)
                .findFirst()
                .orElse(
                        new Exercise(
                                ExercisesData.allExercises.size(),
                                "Что то пошло не так, сообщите администратору сайта"
                        )
                );
    }

    @PostMapping("/add")
    public void addExercise(@RequestBody Exercise exercise) {
        ExercisesData.addExercise(exercise);
    }

    @PostMapping("/delete")
    public void deleteWhereId(@RequestParam Integer exId) {
        ExercisesData.allExercises = ExercisesData
                .allExercises
                .stream()
                .filter(exercise -> exercise.getId() != exId)
                .collect(Collectors.toList());

        ExercisesData.rewriteExercises();
    }
}
