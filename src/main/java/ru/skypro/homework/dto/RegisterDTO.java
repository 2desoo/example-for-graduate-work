package ru.skypro.homework.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterDTO {
    String username;
    String password;
    String firstName;
    String lastName;
    String phone;
    RoleDTO roleDTO;
}
