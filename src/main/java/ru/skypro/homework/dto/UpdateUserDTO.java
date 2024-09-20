package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

// Data Transfer Object для обновления пользователя
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateUserDTO {
    @Schema(description = "Имя")
    @Size(min = 3, max = 10)
    String firstName;
    @Schema(description = "Фамилия")
    @Size(min = 3, max = 10)
    String lastName;
    @Schema(description = "Телефон")
    @Pattern(regexp = "\\+7\\s?\\(?\\d{3}\\)?\\s?\\d{3}-?\\d{2}-?\\d{2}")
    String phone;
}
