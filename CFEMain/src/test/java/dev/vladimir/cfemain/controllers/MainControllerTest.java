package dev.vladimir.cfemain.controllers;

import dev.vladimir.cfecodemodule.baseclasses.Exercise;
import dev.vladimir.cfemain.feign.CodeServiceFeignClient;
import dev.vladimir.cfemain.feign.ExerciseServiceFeignClient;
import dev.vladimir.cfemain.user.models.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MainControllerTest {
    @Mock
    private CodeServiceFeignClient codeServiceFeignClient;

    @Mock
    private ExerciseServiceFeignClient exerciseServiceFeignClient;

    @InjectMocks
    private MainController mainController;

    @Test
    void getMainPage() {
        UserEntity userMock = mock(UserEntity.class);
        Model modelMock = mock(Model.class);
        List<Exercise> exercises = List.of(new Exercise(1, "first ex"), new Exercise(2, "second ex"));
        when(exerciseServiceFeignClient.getAllExercises()).thenReturn(exercises);

        mainController.getMainPage(modelMock, userMock);

        verify(modelMock).addAttribute("exerisesId", exercises
                .stream()
                .map(Exercise::getId)
                .collect(Collectors.toList()));
        verify(modelMock).addAttribute("user", userMock);
    }

    @Test
    void runCodeInSandbox() {
        String code = "Консоль истина ;";
        String codeRunResult = "истина";
        UserEntity userMock = mock(UserEntity.class);
        Model modelMock = mock(Model.class);
        List<Exercise> exercises = List.of(new Exercise(1, "first ex"), new Exercise(2, "second ex"));
        when(exerciseServiceFeignClient.getAllExercises()).thenReturn(exercises);
        when(codeServiceFeignClient.runSandboxCode(code)).thenReturn(codeRunResult);

        mainController.runCodeInSandbox(modelMock, userMock, code);

        verify(modelMock).addAttribute("code", code);
        verify(modelMock).addAttribute("console", codeRunResult);

        verify(modelMock).addAttribute("exerisesId", exercises
                .stream()
                .map(Exercise::getId)
                .collect(Collectors.toList()));
        verify(modelMock).addAttribute("user", userMock);
    }
}