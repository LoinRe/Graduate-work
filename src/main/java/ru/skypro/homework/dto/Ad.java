package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Ad", description = "Краткое объявление")
public class Ad {
    private Integer pk;
    private Integer author;
    private Integer price;
    private String title;
    private String image;
}

