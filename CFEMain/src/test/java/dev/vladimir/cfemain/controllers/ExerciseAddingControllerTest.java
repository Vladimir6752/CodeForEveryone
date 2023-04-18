package dev.vladimir.cfemain.controllers;

import dev.vladimir.cfecodemodule.baseclasses.Exercise;
import dev.vladimir.cfemain.exercise.ExerciseJsonHandler;
import dev.vladimir.cfemain.feign.ExerciseServiceFeignClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.util.ArrayList;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExerciseAddingControllerTest {
    @Mock
    private ExerciseServiceFeignClient exerciseServiceFeignClient;

    @Mock
    private ExerciseJsonHandler exerciseJsonHandler;

    @InjectMocks
    private ExerciseAddingController exerciseAddingController;

    @Test
    void getExercisesAddingPage() {
        Model modelMock = mock(Model.class);
        ArrayList<Exercise> exercises = mock(ArrayList.class);
        when(exercises.size()).thenReturn(5);
        when(exerciseServiceFeignClient.getAllExercises()).thenReturn(exercises);

        exerciseAddingController.getExercisesAddingPage(modelMock);

        verify(modelMock).addAttribute("exId", 6);
    }

    @Test
    void addExercise_shouldAddExercise() {
        String exJson = "{}";
        Model modelMock = mock(Model.class);
        Exercise exerciseMock = mock(Exercise.class);
        ArrayList<Exercise> exercises = mock(ArrayList.class);
        when(exercises.size()).thenReturn(5);
        when(exerciseServiceFeignClient.getAllExercises()).thenReturn(exercises);
        when(exerciseJsonHandler.handleExerciseJson(modelMock, exJson)).thenReturn(exerciseMock);

        exerciseAddingController.addExercise(modelMock, exJson);

        verify(exerciseJsonHandler).handleExerciseJson(modelMock, exJson);
        verify(exerciseServiceFeignClient).addExercise(exerciseMock);
        verify(modelMock).addAttribute("exId", 6);
    }

    @Test
    void addExercise_shouldNotAddExercise() {
        String exJson = "{}";
        Model modelMock = mock(Model.class);
        ArrayList<Exercise> exercises = mock(ArrayList.class);
        when(exercises.size()).thenReturn(5);
        when(exerciseServiceFeignClient.getAllExercises()).thenReturn(exercises);
        when(exerciseJsonHandler.handleExerciseJson(modelMock, exJson)).thenReturn(null);

        exerciseAddingController.addExercise(modelMock, exJson);

        verify(exerciseJsonHandler).handleExerciseJson(modelMock, exJson);
        verify(exerciseServiceFeignClient, times(0)).addExercise(any());
        verify(modelMock).addAttribute("exId", 6);
    }
}