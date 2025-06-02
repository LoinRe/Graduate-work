package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Создание/обновление объявления")
public class CreateOrUpdateAd {
    private String title;      // 4–32 символа
    private Integer price;     // 0–10 000 000
    private String description;// 8–64 символов
}

