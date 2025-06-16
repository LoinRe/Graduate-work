package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "User", description = "Пользователь")
public class User {
    private Integer id;
    private String username;
    private String firstName;
    private String lastName;
    private String phone;
    private String role;  // USER | ADMIN
    private String image;
}
