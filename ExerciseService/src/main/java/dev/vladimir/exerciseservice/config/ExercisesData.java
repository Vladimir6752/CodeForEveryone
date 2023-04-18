package dev.vladimir.exerciseservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import dev.vladimir.cfecodemodule.baseclasses.Exercise;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Legacy code for reading-writing exercises data in json
 */
@Component
@RequiredArgsConstructor
public class ExercisesData {
    private final ObjectMapper mapper;
    private List<Exercise> allExercises = new ArrayList<>();
    private static final String FILE_NAME = "exercises.json";

    public boolean initExercises() {
        String resultJson;

        try(BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            resultJson = sb.toString();
        } catch (Exception e) {
            writeStringOnFile("");
            return false;
        }

        //5 - the new file may contain a new line, or several characters
        if(resultJson.length() < 5) {
            return true;
        }

        return readJsonInAllExercises(resultJson);
    }

    private boolean readJsonInAllExercises(String json) {
        try {
            TypeFactory typeFactory = mapper.getTypeFactory();
            allExercises = mapper.readValue(json, typeFactory.constructCollectionType(List.class, Exercise.class));

            allExercises.sort(Comparator.comparing(Exercise::getId));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    private void writeStringOnFile(String s) {
        try (PrintWriter writer = new PrintWriter(FILE_NAME, StandardCharsets.UTF_8)) {
            writer.write(s);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void rewriteExercisesInFile() {
        try {
            mapper.writeValue(new File(FILE_NAME), allExercises);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addExercise(Exercise exercise)  {
        allExercises.add(exercise);
        rewriteExercisesInFile();
    }

    public List<Exercise> getAllExercises() {
        return allExercises;
    }
}

