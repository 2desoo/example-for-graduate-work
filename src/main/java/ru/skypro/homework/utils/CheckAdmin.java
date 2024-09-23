package ru.skypro.homework.utils;

import org.springframework.stereotype.Component;
import ru.skypro.homework.entity.Role;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.repository.UserRepository;

/**
 * Класс проверяет, является ли пользователь администратором
 */
@Component
public class CheckAdmin {

    private final UserRepository repository;

    public CheckAdmin(UserRepository repository) {
        this.repository = repository;
    }

    public boolean isAdmin(String email) {
        User user = repository.findByEmail(email);
        return user != null && user.getRole().equals(Role.ADMIN);
    }
}
