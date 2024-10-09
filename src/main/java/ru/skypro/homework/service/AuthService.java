package ru.skypro.homework.service;

import ru.skypro.homework.dto.RegisterDTO;

public interface AuthService {
    /**
     * Логика аутентификации пользователя.
     * @param userName - логин пользователя
     * @param password - пароль пользователя
     * @return true, если аутентификация прошла успешно, иначе - false.
     */
    boolean login(String userName, String password);

    /**
     * Логика регистрации нового пользователя.
     * @param registerDTO - информация о пользователе, который необходимо зарегистрировать.
     *                 Модель класса {@link RegisterDTO}
     * @return true, если регистрация прошла успешно, иначе - false.
     */
    boolean register(RegisterDTO registerDTO);
}
