package ru.skypro.homework.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CommentsController.class)
@AutoConfigureMockMvc(addFilters = false) // потом убрать!
class CommentsControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("GET /ads/{id}/comments → 200")
    void getComments() throws Exception {
        mvc.perform(get("/ads/1/comments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count").exists());
    }

    @Test
    @DisplayName("POST /ads/{id}/comments → 200")
    void addComment() throws Exception {
        mvc.perform(post("/ads/1/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"text\":\"testcomment\"}"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("DELETE /ads/{id}/comments/{commentId} → 200")
    void deleteComment() throws Exception {
        mvc.perform(delete("/ads/1/comments/99"))
                .andExpect(status().isOk());
    }
}
