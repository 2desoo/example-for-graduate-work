package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AdsDTO {
    @Schema(description = "количество объявлений")
    Integer count;
    @Schema(description = "массив объявлений")
    AdDTO[] results;
}
