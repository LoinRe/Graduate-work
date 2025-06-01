package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Comment", description = "Комментарий")
public class Comment {
    private Integer pk;
    private Integer author;
    private String authorImage;
    private String authorFirstName;
    private Long createdAt;
    private String text;
}

