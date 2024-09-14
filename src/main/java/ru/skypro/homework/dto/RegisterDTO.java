package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterDTO {
    @Schema(description = "Имя пользователя")
    @Size(min = 4, max = 32)
    @Email
    String username;
    @Schema(description = "Пароль")
    @Size(min = 8, max = 16)
    String password;
    @Schema(description = "Имя")
    @Size(min = 2, max = 16)
    String firstName;
    @Schema(description = "Фамилия")
    @Size(min = 2, max = 16)
    String lastName;
    @Schema(description = "Номер телефона")
    @Pattern(regexp = "^\\+7\\d{10}$", message = "Некорректный номер телефона")
    String phone;
    @Schema(description = "Роль")
    String role;
}
