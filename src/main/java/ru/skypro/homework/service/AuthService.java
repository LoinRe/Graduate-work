package ru.skypro.homework.service;

import ru.skypro.homework.dto.Register;

/**
 * Сервис, отвечающий за аутентификацию и регистрацию.
 */
public interface AuthService {
    /**
     * Проверяет учётные данные пользователя и устанавливает сессию.
     * @param userName имя пользователя (логин)
     * @param password пароль в открытом виде
     * @return true, если учётные данные верны
     */
    boolean login(String userName, String password);

    /**
     * Регистрирует нового пользователя.
     * @param register данные для регистрации
     * @return true, если регистрация успешна
     */
    boolean register(Register register);
}
