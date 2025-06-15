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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.skypro.homework.dto.Ad;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.service.AdService;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdsController.class)
@AutoConfigureMockMvc(addFilters = false)
class AdsControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AdService adService;

    @BeforeEach
    void setUp() {
        Ads ads = new Ads();
        ads.setCount(0);
        ads.setResults(Collections.emptyList());
        when(adService.getAllAds()).thenReturn(Collections.emptyList());
        when(adService.createAd(any(), any())).thenReturn(new Ad());
    }

    @Test
    @DisplayName("GET /ads → 200")
    @WithMockUser
    void getAllAds() throws Exception {
        mvc.perform(get("/ads"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count").exists());
    }

    @Test
    @DisplayName("POST /ads → 201")
    @WithMockUser
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
    @WithMockUser
    void getAd() throws Exception {
        mvc.perform(get("/ads/1"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("DELETE /ads/{id} → 204")
    @WithMockUser
    void deleteAd() throws Exception {
        mvc.perform(delete("/ads/1"))
                .andExpect(status().isNoContent());
    }
}
