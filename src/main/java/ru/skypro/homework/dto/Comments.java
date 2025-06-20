package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(name = "Comments", description = "Ответ со списком комментариев")
public class Comments {
    private Integer count;
    private List<Comment> results;
}
