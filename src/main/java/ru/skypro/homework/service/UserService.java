package ru.skypro.homework.service;

import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.User;
import org.springframework.web.multipart.MultipartFile;

/**
 * Сервис для управления профилем пользователя и аватаром.
 */
public interface UserService {
    /**
     * Изменяет пароль после проверки старого.
     * @param email    email пользователя/логин
     * @param oldPass  старый пароль в открытом виде
     * @param newPass  новый пароль в открытом виде
     * @return true, если пароль успешно изменён
     */
    boolean changePassword(String email, String oldPass, String newPass);
    /**
     * Возвращает информацию о профиле пользователя.
     * @param email email пользователя/логин
     * @return DTO пользователя
     */
    User getUser(String email);
    /**
     * Обновляет поля профиля (имя, фамилия, телефон).
     * @param email user email
     * @param dto   данные для обновления
     * @return тот же DTO для подтверждения
     */
    UpdateUser updateUser(String email, UpdateUser dto);
    /**
     * Сохраняет новое изображение аватара.
     * @param email user email
     * @param image файл изображения
     */
    void updateUserImage(String email, MultipartFile image);
}
