package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.*;

@Tag(name = "Пользователи")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersController {

    @Operation(summary = "Сменить пароль")
    @PostMapping("/set_password")
    public void setPassword(@RequestBody NewPassword body) {
    }

    @Operation(summary = "Получить текущего пользователя")
    @GetMapping("/me")
    public User getUser() {
        return new User();
    }

    @Operation(summary = "Обновить профиль")
    @PatchMapping("/me")
    public UpdateUser updateUser(@RequestBody UpdateUser dto) {
        return dto;
    }

    @Operation(summary = "Обновить аватар")
    @PatchMapping("/me/image")
    @ResponseStatus(HttpStatus.OK)
    public void updateUserImage(@RequestPart MultipartFile image) {
    }
}