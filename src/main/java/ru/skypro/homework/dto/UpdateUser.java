package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Обновление профиля пользователя")
public class UpdateUser {
    private String firstName;
    private String lastName;
    private String phone;
}

