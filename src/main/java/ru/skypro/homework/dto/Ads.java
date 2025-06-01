package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(name = "Ads", description = "Ответ со списком объявлений")
public class Ads {
    private Integer count;
    private List<Ad> results;
}

