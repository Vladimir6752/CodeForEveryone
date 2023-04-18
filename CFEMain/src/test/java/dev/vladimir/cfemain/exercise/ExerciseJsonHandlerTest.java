package dev.vladimir.cfemain.exercise;

import dev.vladimir.cfecodemodule.baseclasses.Exercise;
import dev.vladimir.cfecodemodule.utils.Variable;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
class ExerciseJsonHandlerTest {
    @Autowired
    private ExerciseJsonHandler exerciseJsonHandler;

    @Test
    void handleExerciseJson_working_without_exception() {
        String json = """
                {
                    "id": 3,
                    "title": "Вывести перем",
                    "actualVariablesIn": [
                      [
                        {
                          "name": "перем",
                          "type": "Число[]",
                          "value": "[5, 6]"
                        }
                      ],
                      [
                        {
                          "name": "перем",
                          "type": "Число",
                          "value": "7"
                        }
                      ],
                      [
                        {
                          "name": "перем",
                          "type": "Число",
                          "value": "1"
                        }
                      ]
                    ],
                    "expectedOut": [
                      "5",
                      "7",
                      "1"
                    ]
                  }""";
        Exercise expectedExercise = new Exercise(3, "Вывести перем");
        expectedExercise.getExpectedOut().addAll(List.of("5", "7", "1"));
        expectedExercise.getActualVariablesIn()
                .addAll(
                        List.of(
                                List.of(new Variable("перем", "Число[]", new ArrayList<>(List.of(5,6)))),
                                List.of(new Variable("перем", "Число", String.valueOf(7))),
                                List.of(new Variable("перем", "Число", String.valueOf(1)))
                        )
                );

        Model modelMock = mock(Model.class);
        Exercise actualExercise = exerciseJsonHandler.handleExerciseJson(modelMock, json);

        assertNotNull(actualExercise);
        assertEquals(expectedExercise, actualExercise);

        verify(modelMock).addAttribute("result", String.format("Успешно, %s", expectedExercise));
    }

    @Test
    void handleExerciseJson_working_with_exception() {
        String json = """
                {
                    "id": ,
                    "actualVariablesIn": [
                      [
                        {
                          "name": "перем",
                          "type": "Число[]",
                          "value": "[5, 6]"
                        }
                      ],
                      [
                        {
                          "name": "перем",
                          "type": "Число",
                          "value": "7"
                        }
                      ],
                      [
                        {
                          "name": "перем",
                          "type": "Число",
                          "value": "1"
                        }
                      ]
                    ],
                    "expectedOut": [
                      "5",
                      "7",
                      "1"
                    ]
                  }""";

        Model modelMock = mock(Model.class);

        assertNull(exerciseJsonHandler.handleExerciseJson(modelMock, json));

        verify(modelMock).addAttribute(eq("result"), contains("Unexpected"));
    }
}