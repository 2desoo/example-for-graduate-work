package ru.skypro.homework.service;


import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPasswordDTO;
import ru.skypro.homework.dto.UpdateUserDTO;
import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.repository.UserRepository;

public interface UserService {

    /**
     * Обновляет пароль пользователя
     * Метод использует {@link UserRepository#findByEmail(String)}
     * @param passwordDTO - DTO модель класса {@link NewPasswordDTO}
     */
    Void updatePassword(NewPasswordDTO passwordDTO);

    /**
     * Извлекает текущего аутентифицированного пользователя.
     * Метод использует {@link UserRepository#findByEmail(String)}
     * {@link UserMapper#toUserDTO(User)}
     * @return - объект UserDTO, представляющий текущего пользователя.
     */
    UserDTO getCurrentUser();

    /**
     * Изменение данных пользователя
     * Метод использует {@link UserRepository#findByEmail(String)}
     * {@link UserMapper#updateUserDTOToUser(UpdateUserDTO, User)}
     * {@link UserMapper#toUserDTO(User)}
     * @param updateUserDTO - DTO модель класса {@link UpdateUserDTO}
     * @return - пользователя с измененными данными
     */
    UserDTO updateUser(UpdateUserDTO updateUserDTO);

    /**
     * Метод для обновления аватара пользователя
     * Метод использует {@link UserRepository#findByEmail(String)}
     * @param image - файл аватара
     * @param userName - имя пользователя
     */
    void updateUserImage(MultipartFile image, String userName);

    /**
     * Поиск пользователя по его email
     * Метод использует {@link UserRepository#findByEmail(String)}
     * @param login - email зарегистрированного пользователя
     */
    User findByEmail(String login);

    /**
     * Метод проверяет, что пользователь имеет роль админа
     * Метод использует {@link UserRepository#findByEmail(String)}
     * @param email - email пользователя
     * @return true - если пользователь имеет роль админа, false - в противном случае
     */
    boolean isAdmin(String email);
}
