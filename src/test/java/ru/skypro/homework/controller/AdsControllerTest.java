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

@WebMvcTest(AdsController.class)
@AutoConfigureMockMvc(addFilters = false) // потом убрать!
class AdsControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("GET /ads → 200")
    void getAllAds() throws Exception {
        mvc.perform(get("/ads"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count").exists());
    }

    @Test
    @DisplayName("POST /ads → 201")
    void addAd() throws Exception {
        MockMultipartFile image =
                new MockMultipartFile("image", "pic.jpg",
                        MediaType.IMAGE_JPEG_VALUE, "bytes".getBytes());

        MockMultipartFile props =
                new MockMultipartFile("properties", "",
                        MediaType.APPLICATION_JSON_VALUE,
                        "{\"title\":\"Test\",\"price\":1,\"description\":\"descdesc\"}".getBytes());

        mvc.perform(multipart("/ads")
                        .file(props)
                        .file(image))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("GET /ads/{id} → 200")
    void getAd() throws Exception {
        mvc.perform(get("/ads/1"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("DELETE /ads/{id} → 204")
    void deleteAd() throws Exception {
        mvc.perform(delete("/ads/1"))
                .andExpect(status().isNoContent());
    }
}
