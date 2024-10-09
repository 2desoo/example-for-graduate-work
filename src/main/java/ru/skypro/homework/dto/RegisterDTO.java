package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

// Data Transfer Object для регистрации пользователя
@Data
public class RegisterDTO {
    @Schema(description = "Имя пользователя")
    @Size(min = 4, max = 32)
    @Email
    private String username;

    @Schema(description = "Пароль")
    @Size(min = 8, max = 16)
    private String password;

    @Schema(description = "Имя")
    @Size(min = 2, max = 16)
    private String firstName;

    @Schema(description = "Фамилия")
    @Size(min = 2, max = 16)
    private String lastName;

    @Schema(description = "Номер телефона")
    @Pattern(regexp = "^\\+7\\d{10}$", message = "Некорректный номер телефона")
    private String phone;

    @Schema(description = "Роль")
    private String role;
}
