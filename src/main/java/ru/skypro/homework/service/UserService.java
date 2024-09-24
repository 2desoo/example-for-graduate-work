package ru.skypro.homework.service;


import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPasswordDTO;
import ru.skypro.homework.dto.UpdateUserDTO;
import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.repository.UserRepository;

import java.io.IOException;

public interface UserService {

    /**
     * Обновляет пароль пользователя
     * Метод использует {@link UserRepository#findByEmail(String)}
     * @param passwordDTO - DTO модель класса {@link NewPasswordDTO}
     * @param authentication - аутентифицированный пользователь
     */
    void updatePassword(NewPasswordDTO passwordDTO, Authentication authentication);

    /**
     * Извлекает текущего аутентифицированного пользователя.
     * Метод использует {@link UserRepository#findByEmail(String)}
     * {@link UserMapper#toUserDTO(User)}
     * @param authentication - аутентифицированный пользователь
     * @return - объект UserDTO, представляющий текущего пользователя.
     */
    UserDTO getCurrentUser(Authentication authentication);

    /**
     * Изменение данных пользователя
     * Метод использует {@link UserRepository#findByEmail(String)}
     * {@link UserMapper#updateUserDTOToUser(UpdateUserDTO, User)}
     * {@link UserMapper#toUserDTO(User)}
     * @param updateUserDTO - DTO модель класса {@link UpdateUserDTO}
     * @param authentication - аутентифицированный пользователь
     * @return - пользователя с измененными данными
     */
    UserDTO updateUser(UpdateUserDTO updateUserDTO, Authentication authentication);

    /**
     * Метод для обновления аватара пользователя
     * Метод использует {@link UserRepository#findByEmail(String)}
     * @param image - файл аватара
     * @param userName - имя пользователя
     * @param authentication - аутентифицированный пользователь
     */

    /**
     * Поиск пользователя по его email
     * Метод использует {@link UserRepository#findByEmail(String)}
     * @param login - email зарегистрированного пользователя
     */
    User findByEmail(String login);

    void updateUserImage(MultipartFile image, String userName, Authentication authentication) throws IOException;
}
