package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.*;

import java.util.Collections;

@Tag(name = "Объявления")
@RestController
@RequestMapping("/ads")
@RequiredArgsConstructor
public class AdsController {

    @Operation(summary = "Все объявления")
    @GetMapping
    public Ads getAllAds() {
        Ads ads = new Ads();
        ads.setCount(0);
        ads.setResults(Collections.emptyList());
        return ads;
    }

    @Operation(summary = "Добавить объявление")
    @PostMapping(consumes = "multipart/form-data")
    @ResponseStatus(HttpStatus.CREATED)
    public Ad addAd(@RequestPart("properties") CreateOrUpdateAd props,
                    @RequestPart("image") MultipartFile image) {
        return new Ad();
    }

    @Operation(summary = "Получить объявление")
    @GetMapping("/{id}")
    public ExtendedAd getAd(@PathVariable Integer id) {
        return new ExtendedAd();
    }

    @Operation(summary = "Удалить объявление")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeAd(@PathVariable Integer id) {
    }

    @Operation(summary = "Обновить объявление")
    @PatchMapping("/{id}")
    public Ad updateAd(@PathVariable Integer id,
                       @RequestBody CreateOrUpdateAd dto) {
        return new Ad();
    }

    @Operation(summary = "Мои объявления")
    @GetMapping("/me")
    public Ads getMyAds() {
        Ads ads = new Ads();
        ads.setCount(0);
        ads.setResults(Collections.emptyList());
        return ads;
    }

    @Operation(summary = "Обновить картинку объявления")
    @PatchMapping(value = "/{id}/image", consumes = "multipart/form-data")
    public byte[] updateImage(@PathVariable Integer id,
                              @RequestPart("image") MultipartFile image) {
        return new byte[0];
    }
}
