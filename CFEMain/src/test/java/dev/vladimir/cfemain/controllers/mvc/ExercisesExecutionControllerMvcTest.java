package dev.vladimir.cfemain.controllers.mvc;

import dev.vladimir.cfecodemodule.baseclasses.Exercise;
import dev.vladimir.cfemain.feign.CodeServiceFeignClient;
import dev.vladimir.cfemain.feign.ExerciseServiceFeignClient;
import dev.vladimir.cfemain.user.models.UserEntity;
import dev.vladimir.cfemain.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class ExercisesExecutionControllerMvcTest {
    @MockBean
    private CodeServiceFeignClient codeServiceFeignClient;

    @MockBean
    private ExerciseServiceFeignClient exerciseServiceFeignClient;

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getExercisesExecutionPage_whenUnauthorized_shouldRedirectToLoginPage() throws Exception {
        mockMvc
                .perform(get("/ex"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    void getExercisesExecutionPage_whenAuthorized_shouldShowExercisesExecutionPage() throws Exception {
        Exercise exerciseMock = mock(Exercise.class);
        UserEntity userMock = mock(UserEntity.class);
        when(exerciseServiceFeignClient.getById(1)).thenReturn(exerciseMock);

        mockMvc
                .perform(get("/ex?exId=1").with(user(userMock)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Выполнить задачу")));
    }
}