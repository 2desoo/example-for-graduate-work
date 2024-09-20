package ru.skypro.homework.constant;

import ru.skypro.homework.dto.NewPasswordDTO;
import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.Role;
import ru.skypro.homework.entity.User;

import java.util.List;

public class TestConstants {
    public static final User mockUser = new User(1L, "test@mail.ru", "12345678", "Vova",
            "Dubrovsky", "89198510204", Role.USER, null, null, null);

    public static final UserDTO mockUserDTO = new UserDTO(1, "test@mail.ru", "Vova",
            "Dubrovsky", "89198510204", "USER" , "null");

    public static final NewPasswordDTO mockPasswordDTO = new NewPasswordDTO();

    public static final User user1 = new User(1L, "user@gmail.com", "password",
            "Maria", "Sinyavskaya", "12345678910",
            Role.USER, null, null, null);

    public static final User user2 = new User(2L, "user2@gmail.com", "pass123",
            "Ivan", "Ivanov", "10987654321",
            Role.ADMIN, null, null, null);

    public static final Ad ad1 = new Ad(1L, null, 100, "Title",
            "Description", user1, null);

    public static final Ad ad2 = new Ad(2L, null, 200, "Заголовок",
            "Описание", user2, null);
    public static final List<Ad> list = List.of(ad1, ad2);
}
