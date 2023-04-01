package dev.vladimir.exerciseservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import dev.vladimir.cfecodemodule.baseclasses.Exercise;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Legacy code for reading-writing exercises data in json
 */
@Component
public class ExercisesData {
    private static ObjectMapper mapper;
    public static List<Exercise> allExercises = new ArrayList<>();
    private static final String fileName = "exercises.json";

    public ExercisesData(ObjectMapper mapper1) {
        mapper = mapper1;
    }

    public static boolean initExercises() {
        String resultJson;

        try(BufferedReader br = new BufferedReader(new FileReader(fileName))) {
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

    private static boolean readJsonInAllExercises(String json) {
        try {
            TypeFactory typeFactory = mapper.getTypeFactory();
            allExercises = mapper.readValue(json, typeFactory.constructCollectionType(List.class, Exercise.class));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    private static void writeStringOnFile(String s) {
        try (PrintWriter writer = new PrintWriter(fileName, StandardCharsets.UTF_8)) {
            writer.write(s);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void rewriteExercises() {
        try {
            mapper.writeValue(new File(fileName), allExercises);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void addExercise(Exercise exercise)  {
        allExercises.add(exercise);
        rewriteExercises();
    }
}

