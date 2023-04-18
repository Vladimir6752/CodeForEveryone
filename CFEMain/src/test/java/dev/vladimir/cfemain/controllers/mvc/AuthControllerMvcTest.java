package dev.vladimir.cfemain.controllers.mvc;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerMvcTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void getRegistrationPage_shouldShowRegPage() throws Exception {
        mockMvc
                .perform(get("/reg"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Регистрация")));
    }
}