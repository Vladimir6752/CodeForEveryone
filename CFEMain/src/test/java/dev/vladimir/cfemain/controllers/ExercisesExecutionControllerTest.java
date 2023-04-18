package dev.vladimir.cfemain.controllers;

import dev.vladimir.cfecodemodule.baseclasses.Exercise;
import dev.vladimir.cfemain.feign.CodeServiceFeignClient;
import dev.vladimir.cfemain.feign.ExerciseServiceFeignClient;
import dev.vladimir.cfemain.user.models.UserEntity;
import dev.vladimir.cfemain.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.util.ArrayList;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExercisesExecutionControllerTest {
    @Mock
    private CodeServiceFeignClient codeServiceFeignClient;

    @Mock
    private ExerciseServiceFeignClient exerciseServiceFeignClient;

    @Mock
    private UserService userService;

    @InjectMocks
    private ExercisesExecutionController exercisesExecutionController;

    @Test
    void getExercisesExecutionPage_shouldSetIsSolvedTrue() {
        int exId = 1;
        Model modelMock = mock(Model.class);
        Exercise exerciseMock = mock(Exercise.class);
        UserEntity userMock = mock(UserEntity.class);
        ArrayList<Integer> userSolvedExercisesListMock = mock(ArrayList.class);
        when(userSolvedExercisesListMock.contains(exId)).thenReturn(true);
        when(userMock.getSolvedExercisesId()).thenReturn(userSolvedExercisesListMock);
        when(exerciseServiceFeignClient.getById(exId)).thenReturn(exerciseMock);

        exercisesExecutionController.getExercisesExecutionPage(modelMock, userMock, exId);

        verify(exerciseServiceFeignClient).getById(exId);
        verify(modelMock).addAttribute("exercise", exerciseMock);
        verify(modelMock).addAttribute("isSolved", true);
    }

    @Test
    void getExercisesExecutionPage_shouldSetIsSolvedFalse() {
        int exId = 1;
        Model modelMock = mock(Model.class);
        Exercise exerciseMock = mock(Exercise.class);
        UserEntity userMock = mock(UserEntity.class);
        ArrayList<Integer> userSolvedExercisesListMock = mock(ArrayList.class);
        when(userSolvedExercisesListMock.contains(exId)).thenReturn(false);
        when(userMock.getSolvedExercisesId()).thenReturn(userSolvedExercisesListMock);
        when(exerciseServiceFeignClient.getById(exId)).thenReturn(exerciseMock);

        exercisesExecutionController.getExercisesExecutionPage(modelMock, userMock, exId);

        verify(exerciseServiceFeignClient).getById(exId);
        verify(modelMock).addAttribute("exercise", exerciseMock);
        verify(modelMock).addAttribute("isSolved", false);
    }

    @Test
    void runExerciseCode_shouldAddSolvedExerciseIdInUser() {
        int exId = 1;
        String code = "simple code";
        String codeRunningOutput = "Задача успешно выполнена!";
        Model modelMock = mock(Model.class);
        Exercise exerciseMock = mock(Exercise.class);
        UserEntity userMock = mock(UserEntity.class);
        ArrayList<Integer> userSolvedExercisesListMock = mock(ArrayList.class);
        when(userSolvedExercisesListMock.contains(exId)).thenReturn(false);
        when(userMock.getSolvedExercisesId()).thenReturn(userSolvedExercisesListMock);
        when(exerciseServiceFeignClient.getById(exId)).thenReturn(exerciseMock);
        when(codeServiceFeignClient.runExerciseCode(code, exId)).thenReturn(codeRunningOutput);

        exercisesExecutionController.runExerciseCode(modelMock, userMock, exId, code);

        verify(modelMock).addAttribute("code", code);
        verify(userService).addSolvedExerciseIdInUser(exId, userMock);
        verify(exerciseServiceFeignClient).getById(exId);
        verify(modelMock).addAttribute("output", codeRunningOutput);
        verify(modelMock).addAttribute("exercise", exerciseMock);
        verify(modelMock).addAttribute("isSolved", false);
    }

    @Test
    void runExerciseCode_shouldDontAddSolvedExerciseIdInUser() {
        int exId = 1;
        String code = "simple code";
        String codeRunningOutput = "Some error";
        Model modelMock = mock(Model.class);
        Exercise exerciseMock = mock(Exercise.class);
        UserEntity userMock = mock(UserEntity.class);
        ArrayList<Integer> userSolvedExercisesListMock = mock(ArrayList.class);
        when(userSolvedExercisesListMock.contains(exId)).thenReturn(false);
        when(userMock.getSolvedExercisesId()).thenReturn(userSolvedExercisesListMock);
        when(exerciseServiceFeignClient.getById(exId)).thenReturn(exerciseMock);
        when(codeServiceFeignClient.runExerciseCode(code, exId)).thenReturn(codeRunningOutput);

        exercisesExecutionController.runExerciseCode(modelMock, userMock, exId, code);

        verify(modelMock).addAttribute("code", code);
        verifyNoInteractions(userService);
        verify(exerciseServiceFeignClient).getById(exId);
        verify(modelMock).addAttribute("output", codeRunningOutput);
        verify(modelMock).addAttribute("exercise", exerciseMock);
        verify(modelMock).addAttribute("isSolved", false);
    }
}