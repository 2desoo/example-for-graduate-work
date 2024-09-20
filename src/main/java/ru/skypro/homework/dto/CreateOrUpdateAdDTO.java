package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

// Data Transfer Object для создания или обновления объявления
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateOrUpdateAdDTO {
    @Schema(description = "Заголовок объявления")
    @Size(min = 4, max = 32)
    String title;

    @Schema(description = "Цена объявления")
    @Min(0) @Max(10000000)
    Integer price;

    @Schema(description = "Описание объявления")
    @Size(min = 8, max = 64)
    String description;
}
