package dev.vladimir.cfemain.exercise;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.vladimir.cfecodemodule.baseclasses.Exercise;
import dev.vladimir.cfecodemodule.utils.Variable;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class ExerciseJsonHandler {
    private final ObjectMapper objectMapper;

    public Exercise handleExerciseJson(Model model, String json) {
        Exercise exercise;
        try {
            exercise = objectMapper.readValue(json, Exercise.class);
            handleArrayVariables(exercise);
        } catch (Exception e) {
            model.addAttribute("result", String.format("%s, %s",e.getMessage(), json));
            return null;
        }

        model.addAttribute("result", String.format("Успешно, %s", exercise));
        return exercise;
    }

    private void handleArrayVariables(Exercise exercise) {
        for (List<Variable> variables : exercise.getActualVariablesIn()) {
            for (int i = 0; i < variables.size(); i++) {
                Variable variable = variables.get(i);
                try {
                    variables.set(
                            i,
                            new Variable(
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
