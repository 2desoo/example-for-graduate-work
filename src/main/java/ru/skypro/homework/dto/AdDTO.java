package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AdDTO {
    @Schema(description = "id объявления")
    Integer pk;
    @Schema(description = "id автора объявления")
    Integer author;
    @Schema(description = "ссылка на картинку объявления")
    String image;
    @Schema(description = "цена объявления")
    Integer price;
    @Schema(description = "заголовок объявления")
    String title;
}
