package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Size;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewPasswordDTO {
    @Schema(description = "Текущий пароль")
    @Size(min = 8, max = 16)
    String currentPassword;
    @Schema(description = "Новый пароль")
    @Size(min = 8, max = 16)
    String newPassword;
}
