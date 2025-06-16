package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Запрос на регистрацию")
public class Register {

    private String email;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private Role role; // USER | ADMIN
}
