package dev.vladimir.exerciseservice.controllers;

import dev.vladimir.cfecodemodule.baseclasses.Exercise;
import dev.vladimir.exerciseservice.config.ExercisesData;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/exercise")
@RequiredArgsConstructor
public class ExerciseController {
    private final ExercisesData exercisesData;

    @GetMapping("/get-all")
    public List<Exercise> getAllExercises() {
        return exercisesData.getAllExercises();
    }

    @GetMapping("/get")
    public Exercise getById(@RequestParam Integer exId) {
        return exercisesData
                .getAllExercises()
                .stream()
                .filter(exercise -> exercise.getId() == exId)
                .findFirst()
                .orElse(
                        new Exercise(
                                exercisesData.getAllExercises().size(),
                                "Что то пошло не так, сообщите администратору сайта"
                        )
                );
    }

    @PostMapping("/add")
    public void addExercise(@RequestBody Exercise exercise) {
        exercisesData.addExercise(exercise);
    }

    @PostMapping("/delete")
    public void deleteById(@RequestParam Integer exId) {
        exercisesData
                .getAllExercises()
                .removeIf(exercise -> Objects.equals(exercise.getId(), exId));

        exercisesData.rewriteExercisesInFile();
    }
}
