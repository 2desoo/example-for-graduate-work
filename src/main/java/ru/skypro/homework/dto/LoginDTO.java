package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Size;

// Data Transfer Object для логина
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginDTO {
    @Schema(description = "Имя пользователя")
    @Size(min = 8, max = 16)
    String username;
    @Schema(description = "Пароль")
    @Size(min = 4, max = 32)
    String password;
}
