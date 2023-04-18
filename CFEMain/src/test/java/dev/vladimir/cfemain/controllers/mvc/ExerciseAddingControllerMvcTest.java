package dev.vladimir.cfemain.controllers.mvc;

import dev.vladimir.cfemain.exercise.ExerciseJsonHandler;
import dev.vladimir.cfemain.feign.ExerciseServiceFeignClient;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class ExerciseAddingControllerMvcTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExerciseServiceFeignClient exerciseServiceFeignClient;

    @MockBean
    private ExerciseJsonHandler exerciseJsonHandler;

    @Test
    void getExercisesAddingPage_whenUnauthorized_shouldRedirectToLoginPage() throws Exception {
        mockMvc
                .perform(get("/admin/ex/add"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    @WithMockUser(authorities = { "USER" })
    void getExercisesAddingPage_whenAuthorityOnlyUser_shouldForbidden() throws Exception {
        mockMvc
                .perform(get("/admin/ex/add"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "admin", authorities = { "ADMIN" })
    void getExercisesAddingPage_whenAuthorityAdmin_shouldShowExercisesAddingPage() throws Exception {
        mockMvc
                .perform(get("/admin/ex/add"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Добавить задачу")));
    }
}