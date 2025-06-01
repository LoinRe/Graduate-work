package ru.skypro.homework.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsersController.class)
@AutoConfigureMockMvc(addFilters = false) // потом убрать!
class UsersControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("POST /users/set_password → 200")
    void setPassword() throws Exception {
        mvc.perform(post("/users/set_password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"currentPassword\":\"12345678\",\"newPassword\":\"87654321\"}"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /users/me → 200")
    void getMe() throws Exception {
        mvc.perform(get("/users/me"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("PATCH /users/me → 200")
    void updateUser() throws Exception {
        mvc.perform(patch("/users/me")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"A\",\"lastName\":\"B\",\"phone\":\"+7 999 111-22-33\"}"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("PATCH /users/me/image → 200")
    void updateImage() throws Exception {
        MockMultipartFile img =
                new MockMultipartFile("image", "avatar.png",
                        MediaType.IMAGE_PNG_VALUE, "bytes".getBytes());

        mvc.perform(multipart("/users/me/image").file(img)
                        .with(req -> { req.setMethod("PATCH"); return req; }))
                .andExpect(status().isOk());
    }
}
