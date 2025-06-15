package ru.skypro.homework.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.User;
import ru.skypro.homework.service.UserService;
import ru.skypro.homework.mapper.UserMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsersController.class)
@AutoConfigureMockMvc(addFilters = false)
class UsersControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserMapper userMapper;

    @MockBean
    private Authentication authentication;

    private User testUser;
    private UpdateUser testUpdateUser;

    @BeforeEach
    void setUp() {
        when(authentication.getName()).thenReturn("user@gmail.com");

        testUser = new User();
        testUser.setId(1);
        testUser.setEmail("user@gmail.com");
        testUser.setFirstName("John");
        testUser.setLastName("Doe");
        testUser.setPhone("+7 999 111-22-33");
        testUser.setRole("USER");
        testUser.setImage("image.jpg");
        when(userService.getUser(anyString())).thenReturn(testUser);

        testUpdateUser = new UpdateUser();
        testUpdateUser.setFirstName("John");
        testUpdateUser.setLastName("Doe");
        testUpdateUser.setPhone("+7 999 111-22-33");
        when(userService.updateUser(anyString(), any(UpdateUser.class))).thenReturn(testUpdateUser);
    }

    @Test
    @DisplayName("POST /users/set_password → 200")
    void setPassword() throws Exception {
        when(userService.changePassword(anyString(), anyString(), anyString())).thenReturn(true);

        mvc.perform(post("/users/set_password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"currentPassword\":\"12345678\",\"newPassword\":\"87654321\"}")
                        .principal(authentication))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /users/me → 200")
    void getMe() throws Exception {
        mvc.perform(get("/users/me")
                        .principal(authentication))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("user"))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.phone").value("+7 999 111-22-33"))
                .andExpect(jsonPath("$.role").value("USER"))
                .andExpect(jsonPath("$.image").value("image.jpg"));
    }

    @Test
    @DisplayName("PATCH /users/me → 200")
    void updateUser() throws Exception {
        mvc.perform(patch("/users/me")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"John\",\"lastName\":\"Doe\",\"phone\":\"+7 999 111-22-33\"}")
                        .principal(authentication))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.phone").value("+7 999 111-22-33"));
    }

    @Test
    @DisplayName("PATCH /users/me/image → 200")
    void updateImage() throws Exception {
        MockMultipartFile img =
                new MockMultipartFile("image", "avatar.png",
                        MediaType.IMAGE_PNG_VALUE, "bytes".getBytes());

        mvc.perform(multipart("/users/me/image").file(img)
                        .with(req -> { req.setMethod("PATCH"); return req; })
                        .principal(authentication))
                .andExpect(status().isOk());
    }
}

