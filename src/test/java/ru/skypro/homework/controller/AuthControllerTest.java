package ru.skypro.homework.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.service.AuthService;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    @Autowired MockMvc mvc;
    @MockBean AuthService authService;

    @BeforeEach
    void setUp() {
        when(authService.register(any(Register.class))).thenReturn(true);
        when(authService.login("u", "12345678")).thenReturn(true);
    }

    @Test
    @DisplayName("POST /register → 201")
    void register() throws Exception {
        mvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"user1\",\"password\":\"12345678\","
                                + "\"firstName\":\"Ann\",\"lastName\":\"Bee\","
                                + "\"phone\":\"+7 999 111-22-33\",\"role\":\"USER\"}"))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("POST /login → 200")
    void login() throws Exception {
        mvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"u\",\"password\":\"12345678\"}"))
                .andExpect(status().isOk());
    }
}

