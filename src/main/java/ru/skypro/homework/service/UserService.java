package ru.skypro.homework.service;

import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.entity.User;

public interface UserService {
    User findByEmail(String login);
//    UserDTO getCurrentUser();
}
