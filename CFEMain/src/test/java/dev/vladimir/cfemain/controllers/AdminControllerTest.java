package dev.vladimir.cfemain.controllers;

import dev.vladimir.cfecodemodule.baseclasses.Exercise;
import dev.vladimir.cfemain.feign.ExerciseServiceFeignClient;
import dev.vladimir.cfemain.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminControllerTest {
    @Mock
    private ExerciseServiceFeignClient exerciseServiceFeignClient;

    @Mock
    private UserService userService;

    @InjectMocks
    private AdminController adminController;

    @Test
    void getAdminPage() {
        Model modelMock = mock(Model.class);
        List<Exercise> exercises = List.of(new Exercise(1, "first ex"), new Exercise(2, "second ex"));
        when(exerciseServiceFeignClient.getAllExercises()).thenReturn(exercises);

        adminController.getAdminPage(modelMock);

        verify(modelMock).addAttribute("exercises", exercises);
    }

    @Test
    void deleteExercise() {
        Model modelMock = mock(Model.class);
        List<Exercise> exercises = List.of(new Exercise(1, "first ex"), new Exercise(2, "second ex"));
        Integer exId = 1;
        when(exerciseServiceFeignClient.getAllExercises()).thenReturn(exercises);

        adminController.deleteExercise(modelMock, exId);

        verify(exerciseServiceFeignClient).deleteWhereId(exId);
        verify(userService).deleteSolvedExerciseIdFromUsers(exId);
        verify(modelMock).addAttribute("exercises", exercises);
    }
}