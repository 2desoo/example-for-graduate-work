package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

// Data Transfer Object для обновления пользователя
@Data
public class UpdateUserDTO {
    @Schema(description = "Имя")
    @Size(min = 3, max = 10)
    private String firstName;

    @Schema(description = "Фамилия")
    @Size(min = 3, max = 10)
    private String lastName;

    @Schema(description = "Телефон")
    @Pattern(regexp = "\\+7\\s?\\(?\\d{3}\\)?\\s?\\d{3}-?\\d{2}-?\\d{2}")
    private String phone;
}
