package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

// Data Transfer Object для расширенного объявления
@Data
public class ExtendedAdDTO {
    @Schema(description = "id объявления")
    private Integer pk;
    @Schema(description = "имя автора")
    private String authorFirstName;
    @Schema(description = "фамилия автора")
    private String authorLastName;
    @Schema(description = "описание объявления")
    private String description;
    @Schema(description = "логин автора объявления")
    private String email;
    @Schema(description = "ссылка на картинку объявления")
    private String image;
    @Schema(description = "телефон автора объявления")
    private String phone;
    @Schema(description = "цена объявления")
    private Integer price;
    @Schema(description = "заголовок объявления")
    private String title;
}
