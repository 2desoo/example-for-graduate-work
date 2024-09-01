package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDTO {
    @Schema(description = "Id пользователя")
    Integer id;
    @Schema(description = "Email")
    String email;
    @Schema(description = "Имя пользователя")
    String firstName;
    @Schema(description = "Фамилия пользователя")
    String lastName;
    @Schema(description = "Телефон пользователя")
    String phone;
    @Schema(description = "Роль пользователя")
    RoleDTO roleDTO;
    @Schema(description = "Ссылка на аватар пользователя")
    String image;
}
