package dev.vladimir.cfemain.controllers.mvc;

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

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class MainControllerMvcTest {
    @MockBean
    private ExerciseServiceFeignClient exerciseServiceFeignClient;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getMainPage_whenUnauthorized_shouldRedirectToLoginPage() throws Exception {
        mockMvc
                .perform(get("/"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    @WithMockUser(username = "someUser")
    void getMainPage_whenAuthorized_shouldShowMainPage() throws Exception {
        mockMvc
                .perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Привет,")))
                .andExpect(content().string(containsString("someUser")));
    }
}