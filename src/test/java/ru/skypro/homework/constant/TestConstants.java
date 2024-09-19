package ru.skypro.homework.constant;

import ru.skypro.homework.dto.NewPasswordDTO;
import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.entity.Role;
import ru.skypro.homework.entity.User;

public class TestConstants {
    public static final User mockUser = new User(1L, "test@mail.ru", "12345678", "Vova",
            "Dubrovsky", "89198510204", Role.USER, null, null, null);

    public static final UserDTO mockUserDTO = new UserDTO(1, "test@mail.ru", "Vova",
            "Dubrovsky", "89198510204", "USER" , "null");

    public static final NewPasswordDTO mockPasswordDTO = new NewPasswordDTO();
}
