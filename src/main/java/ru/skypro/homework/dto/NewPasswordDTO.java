package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import javax.validation.constraints.Size;

// Data Transfer Object для смены пароля
@Data
public class NewPasswordDTO {
    @Schema(description = "Текущий пароль")
    @Size(min = 8, max = 16)
    private String currentPassword;

    @Schema(description = "Новый пароль")
    @Size(min = 8, max = 16)
    private String newPassword;
}
