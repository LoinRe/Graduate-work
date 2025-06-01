package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Смена пароля")
public class NewPassword {
    private String currentPassword;
    private String newPassword;
}

