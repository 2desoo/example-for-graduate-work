package ru.skypro.homework.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import ru.skypro.homework.entity.Role;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.exception.AdminAccessException;
import ru.skypro.homework.service.UserService;

/**
 * Класс проверяет, является ли пользователь администратором
 */
@Component
@RequiredArgsConstructor
public class CheckAdmin {

    private final UserService service;


    public boolean isAdmin(String email) {
        User user = service.findByEmail(email);
        return user != null && user.getRole().equals(Role.ADMIN);
    }

    public void checkAdminAccess(Authentication authentication) {
        if (isAdmin(authentication.getName())) {
            throw new AdminAccessException("Администратор не может выполнять это действие");
        }
    }
}
