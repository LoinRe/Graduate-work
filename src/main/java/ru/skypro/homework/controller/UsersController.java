package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.service.UserService;
import ru.skypro.homework.mapper.UserMapper;

@Tag(name = "Пользователи")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersController {
    private final UserService userService;
    private final UserMapper userMapper;

    /**
     * Возвращает профиль текущего пользователя.
     *
     * @param auth аутентификация Spring Security
     * @return DTO пользователя
     */
    @Operation(summary = "Получить текущего пользователя")
    @GetMapping("/me")
    public User getUser(Authentication auth) {
        return userService.getUser(auth.getName());
    }

    /**
     * Обновляет профиль текущего пользователя.
     *
     * @param dto  новые данные профиля
     * @param auth текущий пользователь
     * @return обновлённый DTO
     */
    @Operation(summary = "Обновить профиль")
    @PatchMapping("/me")
    public UpdateUser updateUser(@RequestBody UpdateUser dto, Authentication auth) {
        return userService.updateUser(auth.getName(), dto);
    }

    /**
     * Загружает новую аватарку пользователя.
     *
     * @param image файл изображения
     * @param auth  текущий пользователь
     */
    @Operation(summary = "Обновить аватар")
    @PatchMapping("/me/image")
    @ResponseStatus(HttpStatus.OK)
    public void updateUserImage(@RequestPart MultipartFile image, Authentication auth) {
        userService.updateUserImage(auth.getName(), image);
    }

    @PostMapping("/set_password")
    /**
     * Изменяет пароль пользователя после проверки текущего.
     *
     * @param password объект с текущим и новым паролем
     * @param auth     текущий пользователь
     * @return 200 OK при успехе или 401 при неверном текущем пароле
     */
    @Operation(summary = "Смена пароля пользователя")
    public ResponseEntity<Void> setPassword(@RequestBody NewPassword password, Authentication auth) {
        if (userService.changePassword(auth.getName(), password.getCurrentPassword(), password.getNewPassword())) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}