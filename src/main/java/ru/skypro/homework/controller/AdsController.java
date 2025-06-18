package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.service.AdService;

import java.util.Collections;

@Tag(name = "Объявления")
@RestController
@RequestMapping("/ads")
@RequiredArgsConstructor
public class AdsController {

    private final AdService adService;


    @Operation(summary = "Все объявления")
    @GetMapping
    public Ads getAllAds() {
        Ads ads = new Ads();
        var all = adService.getAllAds();
        ads.setCount(all.size());
        ads.setResults(all);
        return ads;
    }

    @Operation(summary = "Добавить объявление")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Ad> addAd(@RequestPart("properties") String propertiesString,
                                    @RequestPart("image") MultipartFile image,
                                    org.springframework.security.core.Authentication authentication) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            CreateOrUpdateAd properties = objectMapper.readValue(propertiesString, CreateOrUpdateAd.class);
            Ad ad = adService.createAd(properties, image, authentication);
            return ResponseEntity.status(HttpStatus.CREATED).body(ad);
        } catch (JsonProcessingException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Получить объявление")
    @GetMapping("/{id}")
    public ExtendedAd getAd(@PathVariable Integer id) {
        return adService.getAd(id);
    }

    @Operation(summary = "Удалить объявление")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeAd(@PathVariable Integer id,
                         Authentication auth) {
        adService.deleteAd(id, auth);
    }

    @Operation(summary = "Обновить объявление")
    @PatchMapping("/{id}")
    public Ad updateAd(@PathVariable Integer id,
                       @RequestBody CreateOrUpdateAd dto,
                       Authentication auth) {
        return adService.updateAd(id, dto, auth);
    }

    @Operation(summary = "Мои объявления")
    @GetMapping("/me")
    public Ads getMyAds(Authentication auth) {
        Ads ads = new Ads();
        var all = adService.getAdsByUser(auth.getName());
        ads.setCount(all.size());
        ads.setResults(all);
        return ads;
    }

    @Operation(summary = "Обновить картинку объявления")
    @PatchMapping(value = "/{id}/image", consumes = "multipart/form-data")
    public ResponseEntity<String> updateImage(@PathVariable Integer id,
                              @RequestPart("image") MultipartFile image) {
        try {
            String filename = System.currentTimeMillis() + "_" + image.getOriginalFilename();
            java.nio.file.Path mediaDir = java.nio.file.Paths.get("media");
            if (!java.nio.file.Files.exists(mediaDir)) {
                java.nio.file.Files.createDirectories(mediaDir);
            }
            java.nio.file.Path filePath = mediaDir.resolve(filename);
            image.transferTo(filePath);
            // Обновить ссылку на картинку в объявлении
            adService.updateImage(id, "/media/" + filename);
            return ResponseEntity.ok("/media/" + filename);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Ошибка при загрузке изображения: " + e.getMessage());
        }
    }
}
